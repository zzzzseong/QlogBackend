package com.Qlog.backend.controller;

import com.Qlog.backend.consts.ServiceConst;
import com.Qlog.backend.consts.SessionConst;
import com.Qlog.backend.controller.dto.auth.AuthResponse;
import com.Qlog.backend.controller.dto.auth.AuthenticationRequest;
import com.Qlog.backend.controller.dto.auth.RegisterRequest;
import com.Qlog.backend.controller.dto.user.*;
import com.Qlog.backend.domain.User;
import com.Qlog.backend.service.UserService;
import com.Qlog.backend.service.cloud.FileStorageService;
import com.Qlog.backend.service.jwt.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthResponse> authenticate(AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PutMapping("/auth/logout")
    public void logout() {}

    @PostMapping("auth/duplicate")
    public boolean checkDuplication(UserDuplicateCheckForm form) {
        User findUser = userService.findByLoginId(form.getLoginId());
        return (findUser == null);
    }

    @PutMapping("/update")
    public void updateUserProfile(@RequestHeader HttpHeaders header, UserProfileUpdateForm request) {
        userService.UpdateProfile(userService.findByToken(header), request);
    }

    @GetMapping("/read")
    public UserReadResponse readUserInformation(@RequestHeader HttpHeaders header) {
        User findUser = userService.findByToken(header);
        return new UserReadResponse(findUser, fileStorageService.getProfileImageURL(findUser.getProfileImageName()));
    }

    @GetMapping("/readId")
    public Long readUserId(@RequestHeader HttpHeaders header) {
        return userService.findByToken(header).getId();
    }

    @PostMapping("/image/upload")
    public void uploadProfileImage(@RequestHeader HttpHeaders header, UserProfileImageUploadForm request) {
        fileStorageService.uploadProfileImage(userService.findByToken(header), request.getImage());
    }

    @DeleteMapping("/image/remove")
    public String removeProfileImage(@RequestHeader HttpHeaders header) {
        return fileStorageService.removeProfileImage(userService.findByToken(header));
    }
}
