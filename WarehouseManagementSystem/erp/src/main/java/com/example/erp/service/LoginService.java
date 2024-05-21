package com.example.erp.service;

import com.example.erp.entity.User;
import com.example.erp.utils.ResponseResult;

public interface LoginService {
    ResponseResult login(User user);
}
