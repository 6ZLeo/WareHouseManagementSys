package com.example.erp.service.impl;

import com.example.erp.entity.User;
import com.example.erp.repository.UserRepository;
import com.example.erp.service.UserService;
import com.example.erp.utils.UserDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllByRole(String role) {
        return userRepository.findAllByRole(role);
    }
    @Transactional
    public void deleteUser(String userName) {userRepository.deleteByUsername(userName);}

    @Transactional
    public User registerUser(User user) {
        user.setRegistrationDate(new Date());
        // 这里可以添加逻辑，例如检查用户名是否已存在等
        return userRepository.save(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {
            UserDto userDto = new UserDto();
            userDto.setUserID(user.getUserID());
            userDto.setUsername(user.getUsername());
            userDto.setRole(user.getRole().toString());
            userDto.setRegistrationDate(user.getRegistrationDate());
            return userDto;
        }).collect(Collectors.toList());
    }

}
