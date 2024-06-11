package com.example.erp.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class CarTransportConsumer {

    private boolean busy = false;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private CompletableFuture<Boolean> futureResult;

    @Autowired
    public CarTransportConsumer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "car-transport-topic", groupId = "warehouse_group")
    public void handleCarEvent(String message) {
        if (busy) {
            System.out.println("小车忙碌中。");
            return;
        }
        busy = true;

        // 解析消息内容
        String[] parts = message.split(":");
        String productId = parts[0];
        int quantity = Integer.parseInt(parts[1]);

        // 执行小车搬运操作
        boolean success = executeCarOperation(quantity);

        // 更新忙碌状态
        busy = false;

        // 设置处理结果
        if (futureResult != null) {
            futureResult.complete(success);
        }

        // 处理搬运结果
        if (success) {
            System.out.println("小车成功搬运了 " + quantity + " 件物品。");
        } else {
            System.out.println("小车搬运物品失败。");
        }
    }

    private boolean executeCarOperation(int quantity) {
        int batchSize = 1000;
        int batches = (quantity + batchSize - 1) / batchSize; // 向上取整计算批次数

        try {
            for (int i = 0; i < batches; i++) {
                int itemsToMove = Math.min(batchSize, quantity - (i * batchSize));
                System.out.println("正在搬运 " + itemsToMove + " 件物品...");
                Thread.sleep(3000);  // 每批次搬运需要3秒
            }
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public CompletableFuture<Boolean> initiateCarTransport(String message) {
        futureResult = new CompletableFuture<>();
        kafkaTemplate.send("car-transport-topic", message);
        return futureResult;
    }
}
