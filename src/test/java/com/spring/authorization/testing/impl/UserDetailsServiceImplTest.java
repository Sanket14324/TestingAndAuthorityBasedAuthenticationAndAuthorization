package com.spring.authorization.testing.impl;


import com.spring.authorization.testing.authoritiesAndRoles.Role;
import com.spring.authorization.testing.model.User;
import com.spring.authorization.testing.service.impl.UserDetailsServiceImpl;
import com.spring.authorization.testing.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {


    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void loadUserByUsername() {

        User user = new User();
        user.setId("1");
        user.setName("Fake name");
        user.setEmail("fake@gmail.com");
        user.setPassword("12345678");
        user.setRole(Role.ADMIN);

        when(userService.getUserByEmail(anyString())).thenReturn(Optional.of(user));

        UserDetails userDetail = userDetailsService.loadUserByUsername("fake@gmail.com");
//        String role = "";
//        for(GrantedAuthority authority: userDetail.getAuthorities()){
//            role = authority.getAuthority();
//        }


        assertEquals("fake@gmail.com", userDetail.getUsername());
//        assertEquals("ADMIN", userDetail);


    }
}