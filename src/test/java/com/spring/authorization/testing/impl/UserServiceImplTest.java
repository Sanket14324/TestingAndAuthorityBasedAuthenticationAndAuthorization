package com.spring.authorization.testing.impl;


import com.spring.authorization.testing.model.User;
import com.spring.authorization.testing.repository.UserRepository;
import com.spring.authorization.testing.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static com.spring.authorization.testing.authoritiesAndRoles.Role.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {


    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addUser() {
        User user = new User();
        user.setId("hdbuybcdscbuwygjkaknka");
        user.setName("Fake name");
        user.setEmail("fake@gmail.com");
        user.setPassword("12345678");
        user.setRole(ADMIN);


        when(userRepository.save(user)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("ybsdhfbcsjhdbcsdjcueub");

        User actucalUser = userService.addUser(user);

        assertAll(
                ()-> assertNotNull(actucalUser),
                () -> assertEquals("fake@gmail.com", actucalUser.getEmail()),
                () -> assertEquals("Fake name", actucalUser.getName()),
                () -> assertEquals(ADMIN.name(), actucalUser.getRole().name())
        );


    }

    @Test
    void getListOfUser() {
        User user = new User();
        user.setId("hdbuybcdscbuwygjkaknka");
        user.setName("Fake name");
        user.setEmail("fake@gmail.com");
        user.setPassword("12345678");
        user.setRole(ADMIN);

        User user1 = new User();
        user1.setId("hdbuybcdscbuwygvsdcgh");
        user1.setName("Fake1 name");
        user1.setEmail("fake1@gmail.com");
        user1.setPassword("12345678");
        user1.setRole(SUPER_ADMIN);

        User user2 = new User();
        user2.setId("hdbuybcdscbuwygavsduyxh");
        user2.setName("Fake2 name");
        user2.setEmail("fake2@gmail.com");
        user2.setPassword("12345678");
        user2.setRole(USER);


        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);
        List<User> actualUserList = userService.getListOfUser();

        assertAll(
                ()-> assertEquals(3, actualUserList.size()),
                () -> assertEquals("fake@gmail.com", actualUserList.get(0).getEmail()),
                () -> assertEquals("fake1@gmail.com", actualUserList.get(1).getEmail()),
                () -> assertEquals("fake2@gmail.com", actualUserList.get(2).getEmail())
        );

    }

    @Test
    void getUserByEmail() {
        User user = new User();
        user.setId("hdbuybcdscbuwyg");
        user.setName("Fake name");
        user.setEmail("fake@gmail.com");
        user.setPassword("12345678");
        user.setRole(ADMIN);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        Optional<User> userOptional = userService.getUserByEmail("fake@gmail.com");


        assertAll(
                () -> assertNotNull(userOptional),
                () -> assertEquals("Fake name", userOptional.get().getName()),
                () -> assertEquals("fake@gmail.com", userOptional.get().getEmail()),
                () -> assertEquals(ADMIN.name(), userOptional.get().getRole().name())
        );
    }

    @Test
    void deleteUserById() {
        User user = new User();
        user.setId("1");
        user.setName("Fake name");
        user.setEmail("fake@gmail.com");
        user.setPassword("12345678");
        user.setRole(ADMIN);

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        User actualUser = userService.deleteUserById("1");

        assertAll(
                () -> assertNotNull(actualUser),
                () -> assertEquals("fake@gmail.com", actualUser.getEmail()),
                () -> assertEquals("Fake name", actualUser.getName()),
                () -> assertEquals(ADMIN.name(), actualUser.getRole().name())
        );
    }

    @Test
    void getUserByEmailOrId() {

        User user = new User();
        user.setId("1");
        user.setName("Fake name");
        user.setEmail("fake@gmail.com");
        user.setPassword("12345678");
        user.setRole(ADMIN);

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("fake@gmail.com")).thenReturn(Optional.of(user));

        User actualUser = userService.getUserByEmailOrId("1");

        assertAll(
                () -> assertNotNull(actualUser),
                () -> assertEquals("fake@gmail.com", actualUser.getEmail()),
                () -> assertEquals("Fake name", actualUser.getName()),
                () -> assertEquals(ADMIN.name(), actualUser.getRole().name())
        );
        User actualUserByEmail = userService.getUserByEmailOrId("fake@gmail.com");

        assertAll(
                () -> assertNotNull(actualUserByEmail),
                () -> assertEquals("fake@gmail.com", actualUserByEmail.getEmail()),
                () -> assertEquals("Fake name", actualUserByEmail.getName()),
                () -> assertEquals(ADMIN.name(), actualUserByEmail.getRole().name())
        );


    }

    @Test
    void editUserById() {
        User existingUser = new User();
        existingUser.setId("1");
        existingUser.setName("Fake name");
        existingUser.setEmail("fake@gmail.com");
        existingUser.setPassword("12345678");
        existingUser.setRole(ADMIN);

        User updatedUser = new User();
        updatedUser.setId("1");
        updatedUser.setName("FakeEdited name");
        updatedUser.setEmail("fakeEdited@gmail.com");
        updatedUser.setPassword("jhuwednucwnhcbh");
        updatedUser.setRole(ADMIN);

        User raw = new User();
        raw.setName("FakeEdited name");
        raw.setEmail("fakeEdited@gmail.com");
        raw.setPassword("12345678");
        raw.setRole(ADMIN);




        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));

        when(passwordEncoder.encode(anyString())).thenReturn("jhuwednucwnhcbh");

        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User actualUser = userService.editUserById("1", raw);

        assertAll(
                () -> assertNotNull(actualUser),
                () -> assertEquals("1", actualUser.getId()),
                () -> assertEquals("fakeEdited@gmail.com", actualUser.getEmail()),
                () -> assertEquals("FakeEdited name", actualUser.getName()),
                () -> assertEquals(ADMIN.name(), actualUser.getRole().name())
        );


    }
}