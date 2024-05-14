package com.example.erp.service;

import com.example.erp.dto.ProductboundDTO;
import com.example.erp.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    String createProduct(Product product);
    String stockInProduct(ProductboundDTO productdto);
    public String stockOutProduct(ProductboundDTO product);
}

