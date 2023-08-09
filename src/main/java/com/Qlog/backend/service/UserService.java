package com.Qlog.backend.service;

import com.Qlog.backend.controller.dto.user.UserProfileUpdateForm;
import com.Qlog.backend.domain.QCard;
import com.Qlog.backend.domain.User;
import com.Qlog.backend.repository.UserRepository;
import com.Qlog.backend.service.jwt.JwtService;
import com.Qlog.backend.service.storage.RedisCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RedisCacheService redisCacheService;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public void updatePoint(User user, int point) {
        user.updatePoint(point);
    }

    @Transactional
    public void updateProfileImagePath(User user, String path) {
        user.updateProfileImage(path);
    }
    @Transactional
    public void UpdateProfile(User user, UserProfileUpdateForm request) {
        user.updateProfile(request.getUsername(), request.getIntroduction());
    }

    public User findById(Long id) {
        Optional<User> findUser = userRepository.findById(id);
        return findUser.orElse(null);
    }

    public User findByLoginId(String loginId) {
        Optional<User> findUser = userRepository.findByLoginId(loginId);
        return findUser.orElse(null);
    }

    public User findByToken(String token) {
        String loginId = jwtService.getLoginId(token);
        return findByLoginId(loginId);
    }
}