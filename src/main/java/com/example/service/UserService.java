package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.util.Validator;

import java.sql.SQLException;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public boolean registerUser(String name, String email, String password) throws SQLException, ClassNotFoundException {
        Validator.validateEmail(email);

        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("This email is already used.");
        }

        User user = new User(name, email, password);

        userRepository.save(user);
        return true;
    }

    public boolean login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        return user.checkPassword(password);
    }


}