package com.example.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "InboundOrder")
@Data
public class InboundOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inboundOrderID;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

    @Column(name = "InboundQuantity", nullable = false)
    private Integer inboundQuantity;

    @Column(name = "InboundDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date inboundDate;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;
}