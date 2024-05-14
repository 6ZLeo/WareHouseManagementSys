package com.example.erp.controller;

import com.example.erp.entity.User;
import com.example.erp.service.LoginService;
import com.example.erp.utils.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Test
    public void testLogin() throws Exception {
        User mockUser = new User(); // 初始化User对象，按照实际需要进行设置
        ResponseResult expectedResult = new ResponseResult(200, "OK", null); // 根据实际需要设置预期返回结果
        expectedResult.setStatus("成功状态"); // 举例设置预期返回状态

        given(loginService.login(mockUser)).willReturn(expectedResult);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonUser = objectMapper.writeValueAsString(mockUser);

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser))
                .andExpect(status().isOk());

        // 其他断言根据实际情况添加
    }
}
