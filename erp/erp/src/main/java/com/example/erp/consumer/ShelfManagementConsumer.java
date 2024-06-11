package com.example.erp.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class ShelfManagementConsumer {

    private static final int MAX_CAPACITY = 100000;
    private int currentStock = 0;
    private CompletableFuture<Boolean> futureResult;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "shelf-status-topic", groupId = "warehouse_group")
    public void handleShelfEvent(String message) {
        String[] parts = message.split(":");
        String action = parts[0];
        String productId = parts[1];
        int quantity = Integer.parseInt(parts[2]);

        boolean success = false;
        if ("add".equals(action)) {
            if (currentStock + quantity > MAX_CAPACITY) {
                System.out.println("错误：超过最大库存限制。当前库存：" + currentStock + "，尝试添加：" + quantity);
            } else {
                currentStock += quantity;
                System.out.println("已添加 " + quantity + " 件物品。当前库存：" + currentStock);
                success = true;
            }
        } else if ("remove".equals(action)) {
            if (currentStock < quantity) {
                System.out.println("错误：库存不足。当前库存：" + currentStock + "，尝试移除：" + quantity);
            } else {
                currentStock -= quantity;
                System.out.println("已移除 " + quantity + " 件物品。当前库存：" + currentStock);
                success = true;
            }
        } else {
            System.out.println("未知操作：" + action);
        }

        if (futureResult != null) {
            futureResult.complete(success);
        }

        if (success) {
            updateStockInDatabase(productId, currentStock);
        }
    }

    public CompletableFuture<Boolean> initiateShelfUpdate(String message) {
        futureResult = new CompletableFuture<>();
        kafkaTemplate.send("shelf-status-topic", message);
        return futureResult;
    }

    private void updateStockInDatabase(String productId, int currentStock) {
        // 在这里实现更新库存到数据库的逻辑
        // 例如调用仓库管理系统的API或者直接操作数据库
        System.out.println("产品 " + productId + " 的库存已更新为 " + currentStock + " 在数据库中。");
    }
}
