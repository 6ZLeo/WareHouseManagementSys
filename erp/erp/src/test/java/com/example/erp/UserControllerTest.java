package com.example.erp;

import com.example.erp.controller.UserController;
import com.example.erp.dto.UserDto;
import com.example.erp.entity.User;
import com.example.erp.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetUserByUsername() throws Exception {
        UserDto user = new UserDto();

        Mockito.when(userService.findByUsername("admin")).thenReturn(user);

        mockMvc.perform(get("/api/user/susers/admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("admin")));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        UserDto user = new UserDto();
        List<UserDto> users = Collections.singletonList(user);

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/user/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", is("admin")));
    }

    @Test
    public void testRegisterUser() throws Exception {
        User user = new User().setUsername("newuser").setPassword("password");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"newuser\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("已经注册成功了"));

        Mockito.verify(userService).registerUser(Mockito.any(User.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/user/users/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("admin已经成功删除了"));

        Mockito.verify(userService).deleteUser("admin");
    }
}
