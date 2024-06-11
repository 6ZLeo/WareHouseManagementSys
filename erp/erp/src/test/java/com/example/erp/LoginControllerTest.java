package com.example.erp.controller;

import com.example.erp.entity.User;
import com.example.erp.service.LoginService;
import com.example.erp.utils.ResponseResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Test
    public void testLogin() throws Exception {
        User user = new User().setUsername("cxx").setPassword("123456");
        ResponseResult responseResult = new ResponseResult(200, "Login successful", user);

        Mockito.when(loginService.login(Mockito.any(User.class))).thenReturn(responseResult);

        mockMvc.perform(post("/api/user/login")
                        .contentType("application/json")
                        .content("{\"username\":\"cxx\",\"password\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.message", is("Login successful")))
                .andExpect(jsonPath("$.data.username", is("admin")))
                .andExpect(jsonPath("$.data.password", is("123456")));
    }
}
