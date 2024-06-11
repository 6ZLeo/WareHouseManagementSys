package com.example.erp.service.impl;

import com.example.erp.consumer.ShelfManagementConsumer;
import com.example.erp.dto.ProductboundDTO;
import com.example.erp.entity.InboundOrder;
import com.example.erp.entity.OutboundOrder;
import com.example.erp.entity.Product;
import com.example.erp.entity.User;
import com.example.erp.repository.InboundOrderRepository;
import com.example.erp.repository.OutboundOrderRepository;
import com.example.erp.repository.ProductRepository;
import com.example.erp.repository.UserRepository;
import com.example.erp.service.ProductService;
import com.example.erp.consumer.CarTransportConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InboundOrderRepository inboundOrderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OutboundOrderRepository outboundOrderRepository;
    @Autowired
    private CarTransportConsumer carTransportConsumer;
    @Autowired
    private ShelfManagementConsumer shelfManagementConsumer;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public String createProduct(Product product) {
        productRepository.save(product);
        return "创建产品成功";
    }

    @Override
    public String stockInProduct(ProductboundDTO productdto) {
        Optional<Product> productOptional = productRepository.findByProductNameAndSpecification(productdto.getProductName(), productdto.getSpecification());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            String carMessage = product.getProductID() + ":" + productdto.getQuantity();
            CompletableFuture<Boolean> carFutureResult = carTransportConsumer.initiateCarTransport(carMessage);

            try {
                if (carFutureResult.get()) {
                    // 小车搬运成功，发送货架管理消息到Kafka
                    String shelfMessage = "add:" + product.getProductID() + ":" + productdto.getQuantity();
                    CompletableFuture<Boolean> shelfFutureResult = shelfManagementConsumer.initiateShelfUpdate(shelfMessage);

                    if (shelfFutureResult.get()) {
                        // 货架管理成功，更新产品库存数量
                        product.setStockQuantity(product.getStockQuantity() + productdto.getQuantity());
                        productRepository.save(product);

                        // 获取当前认证的用户信息
                        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        User user1 = userDetails.getUser();
                        Optional<User> optionalUser = userRepository.findByUsername(user1.getUsername());
                        User user = optionalUser.orElseThrow(() -> new RuntimeException("未找到用户名为：" + user1.getUsername() + " 的用户"));

                        // 创建并设置 InboundOrder 实体的属性
                        InboundOrder inboundOrder = new InboundOrder();
                        inboundOrder.setProduct(product);
                        inboundOrder.setUser(user);
                        inboundOrder.setInboundQuantity(productdto.getQuantity());
                        inboundOrder.setInboundDate(new Date());
                        inboundOrderRepository.save(inboundOrder);

                        return "库存已更新，入库订单创建成功";
                    } else {
                        return "货架管理失败";
                    }
                } else {
                    return "小车搬运失败";
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return "等待小车搬运结果时发生错误";
            }
        } else {
            return "未找到指定名称和规格的产品";
        }
    }

    @Override
    public String stockOutProduct(ProductboundDTO productdto) {
        Optional<Product> productOptional = productRepository.findByProductNameAndSpecification(
                productdto.getProductName(), productdto.getSpecification());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            String carMessage = product.getProductID() + ":" + productdto.getQuantity();
            CompletableFuture<Boolean> carFutureResult = carTransportConsumer.initiateCarTransport(carMessage);

            try {
                if (carFutureResult.get()) {
                    // 小车搬运成功，发送货架管理消息到Kafka
                    String shelfMessage = "remove:" + product.getProductID() + ":" + productdto.getQuantity();
                    CompletableFuture<Boolean> shelfFutureResult = shelfManagementConsumer.initiateShelfUpdate(shelfMessage);

                    if (shelfFutureResult.get()) {
                        // 货架管理成功，更新产品库存数量
                        product.setStockQuantity(product.getStockQuantity() - productdto.getQuantity());
                        productRepository.save(product);

                        // 获取当前认证的用户信息
                        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        User user1 = userDetails.getUser();
                        Optional<User> optionalUser = userRepository.findByUsername(user1.getUsername());
                        User user = optionalUser.orElseThrow(() -> new RuntimeException("未找到用户名为：" + user1.getUsername() + " 的用户"));

                        // 创建并设置 OutboundOrder 实体的属性
                        OutboundOrder outboundOrder = new OutboundOrder();
                        outboundOrder.setProduct(product);
                        outboundOrder.setUser(user);
                        outboundOrder.setOutboundQuantity(productdto.getQuantity());
                        outboundOrder.setOutboundDate(new Date());
                        outboundOrderRepository.save(outboundOrder);

                        return "库存已更新，出库订单创建成功";
                    } else {
                        return "货架管理失败";
                    }
                } else {
                    return "小车搬运失败";
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return "等待小车搬运结果时发生错误";
            }
        } else {
            return "未找到指定名称和规格的产品";
        }
    }
}

