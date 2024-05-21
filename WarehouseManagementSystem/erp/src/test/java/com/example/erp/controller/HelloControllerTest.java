package com.example.erp.controller;

import com.example.erp.entity.User;
import com.example.erp.utils.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
@ExtendWith(MockitoExtension.class)
public class HelloControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void testHello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"username\":\"admin\",\"password\":\"123456\"}"));
    }

    @Test
    void testHello2() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("key", "value");

        mockMvc.perform(post("/test/api/user/hello")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    ResponseResult response = mapper.readValue(result.getResponse().getContentAsString(), ResponseResult.class);
                    assert response.getStatus().equals(200);
                    assert ((Map<String, String>)response.getBody()).get("message").equals("hello world");
                });
    }

}