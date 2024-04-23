package com.example.erp.service;

import com.example.erp.entity.User;
import com.example.erp.utils.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    List<User> findAllByRole(String role);
    void deleteUser(String userName);

    User registerUser(User user);
    public List<UserDto> getAllUsers();
}