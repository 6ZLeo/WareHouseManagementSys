package com.example.erp.service;

import com.example.erp.entity.User;
import com.example.erp.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto findByUsername(String username);
    List<User> findAllByRole(String role);
    void deleteUser(String userName);

    User registerUser(User user);
    public List<UserDto> getAllUsers();
}
