package com.Qlog.backend.repository;

import com.Qlog.backend.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testUser() {
        User user = new User();
        User savedUser = userRepository.save(user);

        Optional<User> findOptionalUser = userRepository.findById(savedUser.getId());
        if(findOptionalUser.isPresent()) {
            User findUser = findOptionalUser.get();
            Assertions.assertThat(findUser.getId()).isEqualTo(user.getId());
            Assertions.assertThat(findUser).isEqualTo(user);
        }
    }
}
