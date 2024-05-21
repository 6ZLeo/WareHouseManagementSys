package com.example.erp.service.impl;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.erp.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Relation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private User user;
    private List<String> permissions; //permissions是权限列表,就是这个用户分别有哪些权限
    // 用于存储用户权限信息,同时不用每次都去查询，提高效率，但可能导致线程安全问题，权限信息不及时更新
    @JSONField(serialize = false) // 不序列化,防止编译出问题
    private List<SimpleGrantedAuthority> authorities;

    public UserDetailsImpl(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 把permissions中String类型的权限信息封装成SimpleGrantedAuthority(GrantedAuthority的实现类)
        // 第一种方式
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String permission : permissions) {
//            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
//            authorities.add(authority);
//        }

        // 第二种方式
        if (authorities != null) {
            return authorities;
        }
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public User getUser() {
        return user;
    }
}
