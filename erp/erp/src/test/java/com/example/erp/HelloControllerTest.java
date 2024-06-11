package com.example.erp;

import com.example.erp.controller.HelloController;
import com.example.erp.utils.ResponseResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "admin")
    public void testHello() throws Exception {
        mockMvc.perform(get("/test/hello"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\":\"admin\",\"password\":\"123456\"}"));
    }

    @Test
    @WithMockUser(authorities = {"user", "admin"})
    public void testHello2() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("key", "value");

        mockMvc.perform(post("/test/api/user/hello")
                        .contentType("application/json")
                        .content("{\"key\":\"value\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":200,\"message\":\"hello world\",\"data\":{\"key\":\"value\",\"message\":\"hello world\"}}"));
    }
}
