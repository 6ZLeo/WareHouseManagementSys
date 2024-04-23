package com.example.erp.service;

import com.example.erp.entity.Product;
import com.example.erp.utils.ResponseResult;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    String createProduct(Product product);
    String stockInProduct(String productName, String specification, Integer quantity);
    String stockOutProduct(String productName, String specification, Integer quantity);
}

