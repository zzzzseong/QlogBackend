package com.Qlog.backend.controller;

import com.Qlog.backend.consts.ServiceConst;
import com.Qlog.backend.consts.SessionConst;
import com.Qlog.backend.controller.dto.user.*;
import com.Qlog.backend.domain.Role;
import com.Qlog.backend.domain.User;
import com.Qlog.backend.service.UserService;
import com.Qlog.backend.service.cloud.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final FileStorageService fileStorageService;

    @PostMapping("/register")
    public void register(UserRegisterForm form) {
        try {
            User user = new User(form.getLoginId(), form.getPassword(), form.getName(), Role.USER);

            //User 비밀번호 암호화 -> Spring Security 사용법 알아보기

            userService.save(user);
            log.info("REGISTER SUCCESS [{}]", form.getLoginId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/login")
    public boolean login(UserLoginForm form, HttpServletRequest request) {
        User findUser = userService.findByLoginId(form.getLoginId());

        //ID, PW 예외처리
        if(findUser == null) return false; //ID is null
        if(!findUser.getPassword().equals(form.getPassword())) return false; //PW is not correct

        //Session 설정
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", findUser);

        log.info("LOGIN SUCCESS [{}]", form.getLoginId());
        return true;
    }

    @PutMapping("/logout")
    public void logout(HttpServletRequest request,
                       @SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        HttpSession session = request.getSession(false);

        if(session != null) {
            session.invalidate();
            log.info("LOGOUT SUCCESS [{}]", user.getLoginId());
        }
    }

    @PostMapping("/duplication")
    public boolean checkDuplication(UserDuplicateCheckForm form) {
        User findUser = userService.findByLoginId(form.getLoginId());

        //ID 중복: false, ID 사용 가능: true
        return (findUser == null);
    }

    @PutMapping("/update")
    public void updateUserProfile(@SessionAttribute(name = SessionConst.LOGIN_USER) User user,
                                  UserProfileUpdateForm request) {
        if(user == null) return;

        User findUser = userService.findById(user.getId());
        userService.UpdateProfile(findUser, request.getUsername(), request.getIntroduction());
    }

    @GetMapping("/read")
    public UserReadResponse readUserInformation(@SessionAttribute(name= SessionConst.LOGIN_USER) User user) {
        if(user == null) return null;

        User findUser = userService.findById(user.getId());
        String imgPath = "https://qlogbucket.s3.ap-northeast-2.amazonaws.com/user_profile/" + findUser.getProfileImageName();

        return new UserReadResponse(findUser.getName(), findUser.getIntroduction(), findUser.getPoint(),
                findUser.getTier(), imgPath, findUser.getQCards(), findUser.getComments());
    }

    @GetMapping("/readId")
    public Long readUserId(@SessionAttribute(name=SessionConst.LOGIN_USER) User user) {
        return user.getId();
    }

    @PostMapping("/image/upload")
    public void uploadProfileImage(UserProfileImageUploadForm request,
                                   @SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return;
        if(!user.getProfileImageName().equals(ServiceConst.defaultProfileImage)) {
            fileStorageService.removeProfileImage(user.getProfileImageName());
        }
        fileStorageService.uploadProfileImage(user.getId(), request.getImage());
    }

    @DeleteMapping("/image/remove")
    public String removeProfileImage(@SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return null;
        if(user.getProfileImageName().equals(ServiceConst.defaultProfileImage)) return "기본 이미지는 제거할 수 없습니다.";

        User findUser = userService.findById(user.getId());
        fileStorageService.removeProfileImage(findUser.getProfileImageName());

        userService.updateProfileImagePath(findUser, ServiceConst.defaultProfileImage);
        return "ok";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }

}
