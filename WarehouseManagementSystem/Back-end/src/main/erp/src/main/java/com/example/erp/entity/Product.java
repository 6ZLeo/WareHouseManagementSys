package com.example.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Entity
@Table(name = "Product")
@Data
@Accessors(chain = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Integer productID;

    @Column(name = "ProductName", nullable = false)
    private String productName;

    @Column(name = "Specification")
    private String specification;

    @Column(name = "Price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "StockQuantity", nullable = false)
    private Integer stockQuantity;
}
