package com.example.erp;

import com.example.erp.controller.ProductController;
import com.example.erp.dto.ProductboundDTO;
import com.example.erp.entity.Product;
import com.example.erp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testGetProducts() throws Exception {
        Product product = new Product();
        List<Product> products = Collections.singletonList(product);

        Mockito.when(productService.getProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Test Product")));
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product();

        mockMvc.perform(post("/api/products/create")
                        .contentType("application/json")
                        .content("{\"name\":\"New Product\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("创建产品成功"));

        Mockito.verify(productService).createProduct(Mockito.any(Product.class));
    }

    @Test
    public void testStockInProduct() throws Exception {
        mockMvc.perform(post("/api/products/stock/in")
                        .contentType("application/json")
                        .content("{\"productId\":1,\"quantity\":10}"))
                .andExpect(status().isOk())
                .andExpect(content().string("产品入库成功"));

        Mockito.verify(productService).stockInProduct(Mockito.any(ProductboundDTO.class));
    }

    @Test
    public void testStockOutProduct() throws Exception {
        mockMvc.perform(post("/api/products/stock/out")
                        .contentType("application/json")
                        .content("{\"productId\":1,\"quantity\":10}"))
                .andExpect(status().isOk())
                .andExpect(content().string("已成功出库"));

        Mockito.verify(productService).stockOutProduct(Mockito.any(ProductboundDTO.class));
    }
}
