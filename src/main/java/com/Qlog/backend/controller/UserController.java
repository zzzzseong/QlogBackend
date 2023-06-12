package com.Qlog.backend.controller;

import com.Qlog.backend.controller.form.user.UserLoginForm;
import com.Qlog.backend.controller.form.user.UserRegisterForm;
import com.Qlog.backend.domain.User;
import com.Qlog.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void register(UserRegisterForm form) {
        /**
         * user PW 암호화 필요
         * */
        User user = new User(form.getLoginId(), form.getPassword(), form.getName());

        userService.save(user);
    }

    @PostMapping("/login")
    public boolean login(UserLoginForm form, HttpServletRequest request) {
        User findUser = userService.findByLoginId(form.getLoginId());

        //ID, PW 예외처리
        if(findUser == null) return false;
        if(!findUser.getPassword().equals(form.getPassword())) return false;

        //Session 설정
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", findUser);

        log.info("LOGIN SUCCESS [{}]", form.getLoginId());
        return true;
    }
}
