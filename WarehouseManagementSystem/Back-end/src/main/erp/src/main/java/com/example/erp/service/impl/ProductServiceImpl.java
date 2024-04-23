package com.example.erp.service.impl;

import com.example.erp.entity.InboundOrder;
import com.example.erp.entity.Product;
import com.example.erp.entity.User;
import com.example.erp.repository.InboundOrderRepository;
import com.example.erp.repository.ProductRepository;
import com.example.erp.repository.UserRepository;
import com.example.erp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InboundOrderRepository inboundOrderRepository;
    @Autowired
    private UserRepository userRepository;
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
    public String stockInProduct(String productName, String specification, Integer quantity) {
        // 尝试查找指定的产品
        Optional<Product> productOptional = productRepository.findByProductNameAndSpecification(productName, specification);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            // 更新产品库存数量
            product.setStockQuantity(product.getStockQuantity() + quantity);
            productRepository.save(product);

            // 获取当前认证的用户信息
/*          UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long userId = userDetails.getUser().getUserID();*/

            long userid =3;
            // 查找用户实体
            User user = userRepository.findById(userid)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 创建并设置 InboundOrder 实体的属性
            InboundOrder inboundOrder = new InboundOrder();
            inboundOrder.setProduct(product);
            inboundOrder.setUser(user);
            inboundOrder.setInboundQuantity(quantity);
            inboundOrder.setInboundDate(new Date());  // 设置当前时间为入库时间

            // 保存入库记录
            inboundOrderRepository.save(inboundOrder);

            return "Stock updated and inbound order created successfully.";
        } else {
            return "Product not found with the given name and specification.";
        }
    }

    @Override
    public String stockOutProduct(String productName, String specification, Integer quantity) {
        Optional<Product> productOptional = productRepository.findByProductNameAndSpecification(productName, specification);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            int newQuantity = product.getStockQuantity() - quantity;
            if (newQuantity >= 0) {
                product.setStockQuantity(newQuantity);
                productRepository.save(product);
                return "产品出库成功";
            } else {
                return "库存不足，无法出库";
            }
        }
        return "未找到指定产品";
    }
}
