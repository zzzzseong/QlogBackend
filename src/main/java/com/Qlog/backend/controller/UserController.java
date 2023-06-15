package com.Qlog.backend.controller;

import com.Qlog.backend.config.WebSecurityConfig;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final WebSecurityConfig webSecurityConfig;

    @PostMapping("/register")
    public void register(UserRegisterForm form) {
        String encodedPW = webSecurityConfig.getPasswordEncoder().encode(form.getPassword());

        User user = new User(form.getLoginId(), encodedPW, form.getName());

        userService.save(user);
        log.info("REGISTER SUCCESS [{}]", form.getLoginId());
    }

    @PostMapping("/login")
    public boolean login(UserLoginForm form, HttpServletRequest request) {
        User findUser = userService.findByLoginId(form.getLoginId());

        //ID, PW 예외처리
        if(findUser == null) return false;

        if(!webSecurityConfig.getPasswordEncoder().matches(form.getPassword(), findUser.getPassword())) return false;
        if(!findUser.getPassword().equals(form.getPassword())) return false;

        //Session 설정
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", findUser);

        log.info("LOGIN SUCCESS [{}]", form.getLoginId());
        return true;
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

        return new UserReadResponse(user.getName(), user.getPoint());
    }
}
