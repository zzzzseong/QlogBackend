package com.Qlog.backend.controller;

import com.Qlog.backend.consts.ServiceConst;
import com.Qlog.backend.consts.SessionConst;
import com.Qlog.backend.controller.dto.auth.AuthResponse;
import com.Qlog.backend.controller.dto.auth.AuthenticationRequest;
import com.Qlog.backend.controller.dto.auth.RegisterRequest;
import com.Qlog.backend.controller.dto.user.*;
import com.Qlog.backend.domain.Role;
import com.Qlog.backend.domain.User;
import com.Qlog.backend.service.UserService;
import com.Qlog.backend.service.cloud.FileStorageService;
import com.Qlog.backend.service.jwt.AuthService;
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
    private final AuthService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    //시간이 만료되면 알아서 403 forbidden 일어남

    //시간 만료랑 로그아웃시에는 어떻게 하지??
    //access token 과 refresh token의 개념을 잡아놓자
    //근데 어차피 refresh token을 사용한다면 그것 자체로 access token이 되는게 아닌가??
    //

    @PutMapping("/auth/logout") //변경 해야함
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
