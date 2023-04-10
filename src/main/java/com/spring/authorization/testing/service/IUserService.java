package com.spring.authorization.testing.service;



import com.spring.authorization.testing.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User addUser(User user);

    List<User> getListOfUser();

    Optional<User> getUserByEmail(String email);

    User deleteUserById(String id);

    User getUserByEmailOrId(String credentials);

    User editUserById(String credentials, User user);
}
