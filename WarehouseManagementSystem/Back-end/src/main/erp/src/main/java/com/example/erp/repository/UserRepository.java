package com.example.erp.repository;

import com.example.erp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 根据用户名查找用户
    Optional<User> findByUsername(String username);

    // 查找所有管理员用户
    List<User> findAllByRole(String role);

    //根据ID删除用户
    void deleteByUsername(String userName);

    @Query("SELECT u.userID, u.username, u.role, u.registrationDate FROM User u")
    List<Object[]> findUsersWithoutPasswords();
    // 其他特定于业务逻辑的方法...

}