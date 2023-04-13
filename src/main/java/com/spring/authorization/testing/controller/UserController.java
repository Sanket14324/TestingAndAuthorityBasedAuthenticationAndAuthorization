package com.spring.authorization.testing.controller;


import com.spring.authorization.testing.dto.UserDto;
import com.spring.authorization.testing.model.AuthRequest;
import com.spring.authorization.testing.model.User;
import com.spring.authorization.testing.service.impl.JWTService;
import com.spring.authorization.testing.service.impl.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private JWTService jwtService;

    @PostMapping()
    public ResponseEntity<Object> saveUser(@RequestBody @Valid User user){
        User savedUser = userServiceImpl.addUser(user);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(savedUser);
    }

//    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')") // if want to check with role use this
    @PreAuthorize("hasAuthority('user:read')") // if want to check with authority use this
    @GetMapping()
    public List<UserDto> getAllUser(){
        List<User> userList = userServiceImpl.getListOfUser();
        ModelMapper modelMapper = new ModelMapper();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user: userList){
            userDtoList.add(modelMapper.map(user, UserDto.class) );
        }

        return userDtoList;
    }

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/{credentials}")
    public ResponseEntity<Object> getUserByEmailOrId(@PathVariable String credentials){

        User user = userServiceImpl.getUserByEmailOrId(credentials);

        ModelMapper modelMapper = new ModelMapper();

        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDto);

    }


    @PreAuthorize("hasAnyAuthority('user:update')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> putUserById(@RequestHeader("Authorization") String header, @PathVariable String id, @RequestBody User user){

        User editedUser = userServiceImpl.editUserById(id, user, header);
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(editedUser, UserDto.class);
        return ResponseEntity.ok(userDto);

    }


    @PreAuthorize("hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable String id){

        User deletedUser = userServiceImpl.deleteUserById(id);
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(deletedUser, UserDto.class);

        return ResponseEntity.ok(userDto);

    }


    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest) throws ExpiredJwtException {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {

                return jwtService.generateToken(authRequest.getEmail());
            }

            return "Invalid credentials.";
    }
}
