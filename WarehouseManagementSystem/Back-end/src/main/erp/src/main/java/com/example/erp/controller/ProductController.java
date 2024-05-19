package com.example.erp.controller;

import com.example.erp.entity.Product;
import com.example.erp.service.ProductService;
import com.example.erp.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
        productService.createProduct(product);
        return "创建产品成功了";
    }

    // 入库产品
    @PostMapping("/api/products/stock/in")

    public String stockInProduct(@RequestParam String productName, @RequestParam String specification, @RequestParam Integer quantity) {

        System.out.println("运行到入库这里了"+productName+specification+quantity);
        productService.stockInProduct(productName, specification, quantity);
        System.out.println("运行到入库这里了"+productName+specification+quantity);
        return "产品入库成功了";
    }

    // 出库产品fdagadfads
    @PostMapping("/api/products/stock/out")
    public String stockOutProduct(@RequestParam String productName, @RequestParam String specification, @RequestParam Integer quantity) {
        productService.stockOutProduct(productName, specification, quantity);
        return "产品出库成功了";
    }


}
