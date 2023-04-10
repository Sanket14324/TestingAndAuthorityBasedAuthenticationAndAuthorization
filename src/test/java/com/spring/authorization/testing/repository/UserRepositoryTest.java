package com.spring.authorization.testing.repository;


import com.spring.authorization.testing.authoritiesAndRoles.Role;
import com.spring.authorization.testing.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId("hdbuybcdscbuwyg");
        user.setName("Fake name");
        user.setEmail("fake@gmail.com");
        user.setPassword("12345678");
        user.setRole(Role.ADMIN);

        userRepository.save(user);
    }



    @Test
    void findByEmail() {

        Optional<User> actualUser = userRepository.findByEmail("fake@gmail.com");
        assertAll(
                () ->  assertEquals("fake@gmail.com",actualUser.get().getEmail()),
                () ->  assertEquals( "Fake name", actualUser.get().getName()),
                () ->  assertEquals( Role.ADMIN.name(), actualUser.get().getRole().name())
        );

    }

    @Test
    void existByEmail(){
        Boolean condition = userRepository.existsByEmail("fake@gmail.com");
        assertTrue(condition);
    }


    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
}