package com.Qlog.backend.controller;

import com.Qlog.backend.controller.dto.auth.AuthResponse;
import com.Qlog.backend.controller.dto.auth.AuthenticationRequest;
import com.Qlog.backend.controller.dto.auth.RegisterRequest;
import com.Qlog.backend.controller.dto.user.*;
import com.Qlog.backend.domain.User;
import com.Qlog.backend.service.UserService;
import com.Qlog.backend.service.jwt.JwtService;
import com.Qlog.backend.service.storage.FileStorageService;
import com.Qlog.backend.service.jwt.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthResponse> authenticate(AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PutMapping("/auth/logout")
    @PreAuthorize("hasRole('USER')")
    public void logout(@RequestHeader HttpHeaders header) {
        authService.logout(jwtService.getToken(header));
    }

    @PostMapping("auth/duplicate")
    @PreAuthorize("hasRole('USER')")
    public boolean checkDuplication(UserDuplicateCheckForm form) {
        User findUser = userService.findByLoginId(form.getLoginId());
        return (findUser == null);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public void updateUserProfile(@RequestHeader HttpHeaders header, UserProfileUpdateForm request) {
        userService.UpdateProfile(userService.findByToken(jwtService.getToken(header)), request);
    }

    @GetMapping("/read")
    @PreAuthorize("hasRole('USER')")
    public UserReadResponse readUserInformation(@RequestHeader HttpHeaders header) {
        User findUser = userService.findByToken(jwtService.getToken(header));
        return new UserReadResponse(findUser, fileStorageService.getProfileImageURL(findUser.getProfileImageName()));
    }

    @GetMapping("/readId")
    @PreAuthorize("hasRole('USER')")
    public Long readUserId(@RequestHeader HttpHeaders header) {
        return userService.findByToken(jwtService.getToken(header)).getId();
    }

    @PostMapping("/image/upload")
    @PreAuthorize("hasRole('USER')")
    public void uploadProfileImage(@RequestHeader HttpHeaders header, UserProfileImageUploadForm request) {
        fileStorageService.uploadProfileImage(userService.findByToken(jwtService.getToken(header)), request.getImage());
    }

    @DeleteMapping("/image/remove")
    @PreAuthorize("hasRole('USER')")
    public String removeProfileImage(@RequestHeader HttpHeaders header) {
        return fileStorageService.removeProfileImage(userService.findByToken(jwtService.getToken(header)));
    }
}
