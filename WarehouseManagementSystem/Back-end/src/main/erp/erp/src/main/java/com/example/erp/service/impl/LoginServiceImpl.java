package com.example.erp.service.impl;

import com.example.erp.entity.User;
import com.example.erp.service.LoginService;
import com.example.erp.utils.JwtUtil;
import com.example.erp.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager manager;

    @Override
    public ResponseResult login(User user) {
//         AuthenticationManager authenticate进行身份验证
        System.out.println("LoginServiceImpl.login");
        System.out.println("第一次  "+SecurityContextHolder.getContext().getAuthentication());
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authentication = manager.authenticate(authenticationToken);

        // 如果验证不通过，给出错误提示
        if (Objects.isNull(authentication)) {
//            return new ResponseResult(200, "登录失败", null);
            throw new RuntimeException("用户名或密码错误");
        }

        // 如果验证通过，使用userID生成一个jwt jwt存入ResponseResult返回
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("第二次  "+SecurityContextHolder.getContext().getAuthentication());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        String userID = loginUser.getUser().getUserID().toString();
//        String username = user.getUsername();
        String jwt = JwtUtil.createJwt(userDetails);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        map.put("role", userDetails.getUser().getRole().toString());
        System.out.println(map);

        // 把完整的用户信息存入redis userID作为key
        return new ResponseResult(200, "登录成功", map);
//        return null;
    }
}
