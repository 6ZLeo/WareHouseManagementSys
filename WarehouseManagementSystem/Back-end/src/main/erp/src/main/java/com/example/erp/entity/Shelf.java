package com.example.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Entity
@Table(name = "shelf")
@Data
@Accessors(chain = true)
public class Shelf implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShelfID")
    private int shelfId;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @Column(name = "X", nullable = false)
    private int x;

    @Column(name = "Y", nullable = false)
    private int y;

    @Column(name = "container", nullable = false)
    private int container;

    @Column(name = "Capacity", nullable = false)
    private int capacity;
}
