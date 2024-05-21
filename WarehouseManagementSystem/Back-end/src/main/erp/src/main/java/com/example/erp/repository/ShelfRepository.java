package com.example.erp.repository;

import com.example.erp.entity.Product;
import com.example.erp.entity.Shelf;
import com.example.erp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    Optional<Shelf>  findByProduct(Product product);
}
