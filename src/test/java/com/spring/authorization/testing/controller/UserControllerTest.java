package com.spring.authorization.testing.controller;


import com.spring.authorization.testing.dto.UserDto;
import com.spring.authorization.testing.model.AuthRequest;
import com.spring.authorization.testing.model.User;
import com.spring.authorization.testing.service.impl.JWTService;
import com.spring.authorization.testing.service.impl.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;


import static com.spring.authorization.testing.authoritiesAndRoles.Role.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
class UserControllerTest {


    @InjectMocks
    UserController userController;
    @Mock
    UserServiceImpl userService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveUser() {
        User user = new User();
        user.setId("hdbuybcdscbuwygjkaknka");
        user.setName("Fake name");
        user.setEmail("fake@gmail.com");
        user.setPassword("12345678");
        user.setRole(ADMIN);


        when(userService.addUser(user)).thenReturn(user);


        ResponseEntity<Object> actualResponseUser = userController.saveUser(user);
        User actualUser =(User) actualResponseUser.getBody();

        assertAll(
                ()-> assertNotNull(actualUser),
                () -> assertEquals("fake@gmail.com", actualUser.getEmail()),
                () -> assertEquals("Fake name", actualUser.getName()),
                () -> assertEquals(ADMIN.name(), actualUser.getRole().name())
        );
    }

    @Test
    void getAllUser() {
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

        when(userService.getListOfUser()).thenReturn(userList);
        List<UserDto> actualUserList = userController.getAllUser();

        assertAll(
                ()-> assertEquals(3, actualUserList.size()),
                () -> assertEquals("fake@gmail.com", actualUserList.get(0).getEmail()),
                () -> assertEquals("fake1@gmail.com", actualUserList.get(1).getEmail()),
                () -> assertEquals("fake2@gmail.com", actualUserList.get(2).getEmail())
        );
    }

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;



    @Test
    @Disabled
    public void testAuthenticateAndGetTokenWithValidCredentials() throws ExpiredJwtException {
        AuthRequest authRequest = new AuthRequest("testuser@example.com", "password");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        when(jwtService.generateToken(authRequest.getEmail())).thenReturn(token);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Act
        String result = userController.authenticateAndGetToken(authRequest);

        // Assert
        assertEquals(token, result);
    }

    @Test
    void putUserById() {

        User rawUser = new User();
        rawUser.setEmail("fakeupdated@gmail.com");
        rawUser.setName("FakeUpdated name");
        rawUser.setRole(USER);
        rawUser.setPassword("123456789");

        User updatedUser = new User();
        updatedUser.setId("1");
        updatedUser.setEmail("fakeupdated@gmail.com");
        updatedUser.setName("FakeUpdated name");
        updatedUser.setRole(USER);
        updatedUser.setPassword("123456789");


        when(userService.editUserById("1", rawUser)).thenReturn(updatedUser);

        ResponseEntity<Object> responseUser = userController.putUserById("1", rawUser);

        UserDto actualUser  = (UserDto) responseUser.getBody();

        assertAll(
                () -> assertEquals("FakeUpdated name", actualUser.getName()),
                () -> assertEquals("fakeupdated@gmail.com", actualUser.getEmail())
        );
    }

    @Test
    void deleteUserById() {
        User deletedUser = new User();
        deletedUser.setId("1");
        deletedUser.setEmail("fake@gmail.com");
        deletedUser.setName("Fake name");
        deletedUser.setRole(USER);
        deletedUser.setPassword("123456789");


        when(userService.deleteUserById("1")).thenReturn(deletedUser);

        ResponseEntity<Object> responseUser = userController.deleteUserById("1");

        UserDto actualUser  = (UserDto) responseUser.getBody();

        assertAll(
                () -> assertEquals("Fake name", actualUser.getName()),
                () -> assertEquals("fake@gmail.com", actualUser.getEmail())
        );
    }

    @Test
    void getUserByEmailOrId() {
        User user = new User();
        user.setId("1");
        user.setEmail("fake@gmail.com");
        user.setName("Fake name");
        user.setRole(USER);
        user.setPassword("hsvcuhdsBcuysbhc");



        when(userService.getUserByEmailOrId(anyString())).thenReturn(user);

        ResponseEntity<Object> responseObject = userController.getUserByEmailOrId("fake@gmail.com");

        UserDto actualUser =(UserDto) responseObject.getBody();

        assertAll(
                () -> assertNotNull(actualUser),
                () -> assertEquals("Fake name", actualUser.getName()),
                () -> assertEquals("fake@gmail.com", actualUser.getEmail()),
                () -> assertEquals("USER", actualUser.getRole()),
                () -> assertEquals("1", actualUser.getId())
        );

        ResponseEntity<Object> responseObject1 = userController.getUserByEmailOrId("1");

        UserDto actualUser1 =(UserDto) responseObject1.getBody();

        assertAll(
                () -> assertNotNull(actualUser),
                () -> assertEquals("Fake name", actualUser.getName()),
                () -> assertEquals("fake@gmail.com", actualUser.getEmail()),
                () -> assertEquals("USER", actualUser.getRole()),
                () -> assertEquals("1", actualUser.getId())
        );



    }
}
