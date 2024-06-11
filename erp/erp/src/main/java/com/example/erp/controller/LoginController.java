package com.example.erp.controller;

import com.example.erp.entity.User;
import com.example.erp.service.LoginService;
import com.example.erp.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/api/user/login")
    public ResponseResult login(@RequestBody User user) {
        // 使用登录Service的login方法
        return loginService.login(user);
    }
}
