package com.example.erp.repository;

public interface InventoryRepository {

    // 查询商品库存数量
    Integer getStockQuantityByProductId(Long productId);

    // 调整商品库存数量
    void adjustStockQuantity(Long productId, Integer adjustment);

    // 其他库存管理的操作...
}
