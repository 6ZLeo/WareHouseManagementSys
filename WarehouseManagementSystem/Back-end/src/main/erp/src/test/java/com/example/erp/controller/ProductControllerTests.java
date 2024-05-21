package com.example.erp.controller;

import com.example.erp.entity.Product;
import com.example.erp.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("Get All Product test")
    public void getProductsTest() throws Exception {
        Product product1 = new Product();
        product1.setProductName("TestProduct1");
        product1.setSpecification("Spec1");
        product1.setPrice(new BigDecimal("10.00"));
        product1.setStockQuantity(100);

        Product product2 = new Product();
        product2.setProductName("TestProduct2");
        product2.setSpecification("Spec2");
        product2.setPrice(new BigDecimal("20.00"));
        product2.setStockQuantity(200);

        List<Product> allProducts = Arrays.asList(product1, product2);

        when(productService.getProducts()).thenReturn(allProducts);

        mockMvc.perform(get("/api/products/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(allProducts)));

        verify(productService, times(1)).getProducts();
    }

    @Test
    @WithMockUser
    @DisplayName("Create Product test")
    public void createProductTest() throws Exception {
        // 创建待测试的产品对象
        Product newProduct = new Product();
        newProduct.setProductName("NewProduct");
        newProduct.setSpecification("NewSpec");
        newProduct.setPrice(new BigDecimal("50.00"));
        newProduct.setStockQuantity(25);


        // 发送 POST 请求并传递产品数据，同时包含 CSRF 令牌
        mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("创建产品成功"));

        // 验证 productService 的 createProduct 方法是否用正确参数被调用了一次
        verify(productService, times(1)).createProduct(refEq(newProduct));
    }

    @Test
    @WithMockUser
    @DisplayName("Stock In Product test")
    public void stockInProductTest() throws Exception {
        String productName = "ExistingProduct";
        String specification = "ExistingSpec";
        Integer quantity = 10;
        String expectedResponse = "产品入库成功";

        // Mock the productService's stockInProduct to return the expected response
        when(productService.stockInProduct(anyString(), anyString(), anyInt())).thenReturn(expectedResponse);

        // Perform POST request with all required parameters and include CSRF token
        mockMvc.perform(post("/api/products/stock/in")
                        .param("productName", productName)
                        .param("specification", specification)
                        .param("quantity", quantity.toString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        // Verify that stockInProduct was called on productService with the correct parameters
        verify(productService, times(1)).stockInProduct(eq(productName), eq(specification), eq(quantity));
    }

    @Test
    @WithMockUser
    @DisplayName("Stock Out Product test")
    public void stockOutProductTest() throws Exception {
        String productName = "ExistingProduct";
        String specification = "ExistingSpec";
        Integer quantity = 5;
        String expectedResponse = "产品出库成功";

        // Mock the productService's stockOutProduct to return the expected response
        when(productService.stockOutProduct(anyString(), anyString(), anyInt())).thenReturn(expectedResponse);

        // Perform POST request with all required parameters and include CSRF token
        mockMvc.perform(post("/api/products/stock/out")
                        .param("productName", productName)
                        .param("specification", specification)
                        .param("quantity", quantity.toString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        // Verify that stockOutProduct was called on productService with the correct parameters
        verify(productService, times(1)).stockOutProduct(eq(productName), eq(specification), eq(quantity));
    }


}