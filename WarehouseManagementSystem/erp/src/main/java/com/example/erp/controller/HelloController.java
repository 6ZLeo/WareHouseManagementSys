package com.example.erp.controller;

import com.alibaba.fastjson2.JSON;
import com.example.erp.entity.User;
import com.example.erp.utils.ResponseResult;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("test")
@SpringBootApplication
public class HelloController {

    @ResponseBody
//    @PreAuthorize("hasRole('ROLE_admin')")
//    @PreAuthorize("@auEx.hasAuthority('admin')")
    @GetMapping("/hello")
    public String hello() {
        System.out.println("hello world");
        String str = JSON.toJSONString(new User().setUsername("admin").setPassword("123456"));
        return str;
    }

//    @PreAuthorize("@auEx.hasAuthority('user')")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    @PostMapping("/api/user/hello")
    public ResponseResult hello2(@RequestBody Map<String, String> requestBody) {
        System.out.println(requestBody);
        requestBody.put("message", "hello world");
        System.out.println(requestBody);
        return new ResponseResult(200, "hello world", requestBody);
    }
}
