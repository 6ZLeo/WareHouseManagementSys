package com.example.erp.controller;

import com.example.erp.entity.User;
import com.example.erp.service.UserService;
import com.example.erp.service.impl.UserServiceImpl;
import com.example.erp.utils.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        sampleUser = new User()
                .setUserID(1)
                .setUsername("userTest")
                .setPassword("password")
                .setRole(User.Role.user)
                .setRegistrationDate(new java.util.Date());
        // 创建并配置一个模拟User对象
        User mockUser = new User();
        mockUser.setUsername("userTest");

        Mockito Mockito = null;
        Mockito.when(userService.findByUsername("userTest")).thenReturn(Optional.of(mockUser));
    }

    @Test
    public void getUserByUsernameTest() throws Exception {
        // 执行GET请求
        mockMvc.perform(get("/api/user/susers/userTest")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("userTest"));
    }

    @Test
    public void getAllUsersTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("userTest");
        // 假设getAllUsers返回的是一个包含一个UserDto对象的列表
        given(userService.getAllUsers()).willReturn(Arrays.asList(userDto));

        mockMvc.perform(get("/api/user/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("userTest"));
    }

    @Test
    public void registerUserTest() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"userTest\", \"password\": \"password\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string("已经注册成功了"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/api/user/users/userTest"))
                .andExpect(status().isOk())
                .andExpect(content().string("userTest已经成功删除了"));
    }
}