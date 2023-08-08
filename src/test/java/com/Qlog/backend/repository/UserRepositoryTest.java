package com.Qlog.backend.repository;

import com.Qlog.backend.domain.QCard;
import com.Qlog.backend.domain.Role;
import com.Qlog.backend.domain.User;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUser() {
        User user = new User("test", "test", "test", Role.USER);
        User savedUser = userRepository.save(user);

        Optional<User> findOptionalUser = userRepository.findById(savedUser.getId());
        if(findOptionalUser.isPresent()) {
            User findUser = findOptionalUser.get();
            Assertions.assertThat(findUser.getId()).isEqualTo(user.getId());
            Assertions.assertThat(findUser).isEqualTo(user);
        }
    }

//    @Test
//    public void testLAZY() {
//        User user = new User("test", "test", "test");
//        User saveUser = userRepository.save(user);
//
//        User findUser = userRepository.findById(saveUser.getId()).get();
//        List<QCard> qCards = findUser.getQCards();
//        for (QCard qCard : qCards) {
//            System.out.println("test");
//        }
//    }
}
