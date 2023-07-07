package com.Qlog.backend.service;

import com.Qlog.backend.domain.User;
import com.Qlog.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

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

    public User findById(Long id) {
        Optional<User> findUser = userRepository.findById(id);
        return findUser.orElse(null);
    }

    public User findByLoginId(String loginId) {
        Optional<User> findUser = userRepository.findByLoginId(loginId);
        return findUser.orElse(null);
    }
}