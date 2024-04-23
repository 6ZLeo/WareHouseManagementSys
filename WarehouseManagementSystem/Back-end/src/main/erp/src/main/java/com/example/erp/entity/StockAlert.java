package com.example.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "StockAlert")
@Data
public class StockAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer alertID;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

    @Column(name = "AlertThreshold", nullable = false)
    private Integer alertThreshold;

    @Column(name = "CreateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
}
