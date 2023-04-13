package com.spring.authorization.testing.service.impl;


import com.spring.authorization.testing.authoritiesAndRoles.Role;
import com.spring.authorization.testing.model.User;
import com.spring.authorization.testing.repository.UserRepository;
import com.spring.authorization.testing.service.IUserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @NotNull
    @Override
    public User addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public List<User> getListOfUser(){
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public Optional<User> getUserByEmail(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);

        return userOptional;
    }




    @Override
    public User deleteUserById(String id) {
            User user = userRepository.findById(id).orElseThrow(() ->
                    new RuntimeException("User Not Found with Id - "+ id));
            userRepository.deleteById(user.getId());

            return user;


    }

    @Override
    public User getUserByEmailOrId(String credentials) {
        Optional<User> userByEmail = userRepository.findByEmail(credentials);
        if(userByEmail.isPresent()){
            return userByEmail.get();
        }
        else{

            User user = userRepository.findById(credentials).orElseThrow(() ->
                    new RuntimeException("User Not Found with credentials - "+ credentials));
            return user;
        }
    }

    @Override
    public User editUserById(String id, User user, String header) {
        // getting user from the token
        String token = header.substring(7);
        String email =  jwtService.extractUsername(token);
        Optional<User> existingUser = userRepository.findByEmail(email);
        if(existingUser.isPresent()){
            // if User's role is User then check passed id and user's id is same or not to verify same user
            // updating information

            // and if the role is super admin then Allow to update
            if((existingUser.get().getRole().equals(Role.USER) && existingUser.get().getId().equals(id)) ||
                    existingUser.get().getRole().equals(Role.SUPER_ADMIN)){
                User editUser = userRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("User Not Found with credentials - "+ id));
                editUser.setEmail(user.getEmail());
                editUser.setName(user.getName());
                editUser.setRole(user.getRole());
                editUser.setPassword(passwordEncoder.encode(user.getPassword()));
                User finalUser = userRepository.save(editUser);
                return finalUser;
            }
            else {
                throw new RuntimeException("You are Unauthorized to update this user with Id - "+ id);
            }
        }
        throw new RuntimeException("You are Unauthorized to update this user with Id - "+ id);
    }
    }

