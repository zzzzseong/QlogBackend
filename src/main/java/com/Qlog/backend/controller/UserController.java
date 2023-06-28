package com.Qlog.backend.controller;

import com.Qlog.backend.consts.SessionConst;
import com.Qlog.backend.controller.dto.user.UserLoginForm;
import com.Qlog.backend.controller.dto.user.UserDuplicateCheckForm;
import com.Qlog.backend.controller.dto.user.UserReadResponse;
import com.Qlog.backend.controller.dto.user.UserRegisterForm;
import com.Qlog.backend.domain.User;
import com.Qlog.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void register(UserRegisterForm form) {
        try {
            User user = new User(form.getLoginId(), form.getPassword(), form.getName());

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

    @GetMapping("/read")
    public UserReadResponse readUserInformation(@SessionAttribute(name= SessionConst.LOGIN_USER) User user) {
        if(user == null) return null;

        User findUser = userService.findById(user.getId());
        return new UserReadResponse(findUser.getName(), findUser.getPoint(), findUser.getTier(), findUser.getQCards());
    }

//    @GetMapping("/image")
//    public MultipartFile readUserProfileImage(@SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
//        if(user == null) return null;
//
//    }
}
