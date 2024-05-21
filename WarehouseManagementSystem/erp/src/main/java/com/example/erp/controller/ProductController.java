package com.example.erp.controller;

import com.example.erp.dto.ProductboundDTO;
import com.example.erp.entity.Product;
import com.example.erp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;


    //获取所有的产品
    @GetMapping("/api/products/all")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    // 创建新的产品
    @PostMapping("/api/products/create")
    public String createProduct(@RequestBody Product product) {
        System.out.println(product);
        productService.createProduct(product);
        return "创建产品成功";
    }

     //入库产品
    @PostMapping("/api/products/stock/in")
    public String stockInProduct(@RequestBody ProductboundDTO productdto) {
        productService.stockInProduct(productdto);
        return "产品入库成功";
    }


    // 出库产品
    @PostMapping("/api/products/stock/out")
    public String stockOutProduct(@RequestBody ProductboundDTO productdto) {
        productService.stockOutProduct(productdto);
        return "已成功出库";
    }


}
