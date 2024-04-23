package com.example.erp.controller;

import com.example.erp.entity.User;
import com.example.erp.service.UserService;
import com.example.erp.service.impl.UserServiceImpl;
import com.example.erp.utils.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("api/user/susers/{username}")
    public User getUserByUsername(@PathVariable String username) {
        System.out.println("运行到用用户名查找用户了");
        Optional<User> user = userService.findByUsername(username);
        if (!user.isPresent())
            return null;
        return user.get();

    }

    @GetMapping("api/user/users")
    public List<UserDto> getAllUsers() {
        System.out.println("已经运行到查询所有用户了");
        List<UserDto> users = userService.getAllUsers();
        System.out.println(users);
        return users;
    }

    @PostMapping("users")
    public ResponseEntity registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("已经注册成功了");
    }

    @DeleteMapping("api/user/users/{userName}")
    public ResponseEntity deleteUser(@PathVariable String userName) {
        System.out.println("我要删除");
        userService.deleteUser(userName);
        System.out.println("删除成功了");
        // 响应成功删除
        return ResponseEntity.ok(userName+"已经成功删除了");
    }
}
