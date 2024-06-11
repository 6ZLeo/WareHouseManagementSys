package com.example.erp.expression;

import com.example.erp.service.impl.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

// 自定义权限表达式,,检查当前认证用户是否拥有特定的权限
@Component("auEx")
public class AuthorityExpression {

    // 判断当前用户是否具有指定角色
    public boolean hasAuthority(String authority) {
        // 获取当前用户的权限列表
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> permissions = userDetails.getPermissions();

        // 判断用户列表是否包含指定权限
        return permissions.contains(authority);
    }
}
