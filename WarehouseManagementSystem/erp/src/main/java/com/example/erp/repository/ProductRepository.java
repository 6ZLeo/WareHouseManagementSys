package com.example.erp.repository;

import com.example.erp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 根据商品名称查找商品
    List<Product> findByProductName(String productName);

    // 根据规格查找商品
    List<Product> findBySpecification(String specification);
    Optional<Product> findByProductNameAndSpecification(String productName, String specification);

    //根据商品名称和型号查找商品ID

    // 获取所有的商品
    List<Product> findAll();

    // 创建新的商品
    Product save(Product product);

    // 其他特定于业务逻辑的方法...
}
