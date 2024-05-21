package com.example.erp.service.impl;

import com.example.erp.entity.User;
import com.example.erp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        // 查询用户信息
        Optional<User> user = repository.findByUsername(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // TODO 查询对应的权限信息
//        List<String> list = new ArrayList<>(Arrays.asList("test", "admin"));
        List<String> list = new ArrayList<>(Arrays.asList(user.get().getRole().toString()));

        // 把用户信息封装成UserDetails对象返回
        return new UserDetailsImpl(user.get(), list);
    }
}
