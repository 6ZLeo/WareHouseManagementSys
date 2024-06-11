package com.example.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "OutboundOrder")
@Data
public class OutboundOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer outboundOrderID;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

    @Column(name = "OutboundQuantity", nullable = false)
    private Integer outboundQuantity;

    @Column(name = "OutboundDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date outboundDate;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;
}