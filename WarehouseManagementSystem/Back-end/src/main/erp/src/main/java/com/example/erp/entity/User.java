package com.example.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Entity
@Table(name = "User")
@Data
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(name = "Username", nullable = false, unique = true)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password; // 注意：在生产环境中，密码应该被安全地哈希和盐值化

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private Role role;

    @Column(name = "RegistrationDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    public enum Role {
        admin, user
    }
}
