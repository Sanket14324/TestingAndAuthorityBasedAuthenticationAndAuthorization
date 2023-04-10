package com.spring.authorization.testing.service.impl;

import com.spring.authorization.testing.model.User;
import com.spring.authorization.testing.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserServiceImpl userServiceImpl;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userServiceImpl.getUserByEmail(email);
        return userOptional.map(UserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + email));
    }
}
