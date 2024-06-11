package com.example.erp.service.impl;

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
    @Autowired
    private OutboundOrderRepository outboundOrderRepository;
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
        // 尝试查找指定的产品
        Optional<Product> productOptional = productRepository.findByProductNameAndSpecification(productdto.getProductName(), productdto.getSpecification());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            // 更新产品库存数量
            product.setStockQuantity(product.getStockQuantity() + productdto.getQuantity());
            productRepository.save(product);

            // 获取当前认证的用户信息
/*          UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long userId = userDetails.getUser().getUserID();*/


            // 查找用户实体
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user1 = userDetails.getUser();



            Optional<User> optionalUser = userRepository.findByUsername(user1.getUsername());


            // 检查是否找到用户，如果没找到，抛出异常
            User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found with username: " + user1));
            System.out.println("获取的user"+user1);
            // 创建并设置 InboundOrder 实体的属性
            InboundOrder inboundOrder = new InboundOrder();
            inboundOrder.setProduct(product);
            inboundOrder.setUser(user);
            inboundOrder.setInboundQuantity(productdto.getQuantity());
            inboundOrder.setInboundDate(new Date());  // 设置当前时间为入库时间

            // 保存入库记录
            inboundOrderRepository.save(inboundOrder);

            return "Stock updated and inbound order created successfully.";
        } else {
            return "Product not found with the given name and specification.";
        }
    }

    @Override
    public String stockOutProduct(ProductboundDTO productdto) {
        OutboundOrder outboundOrder = new OutboundOrder();
        //根据名称和规格获取对应的product实例
        Optional<Product> productOptional = productRepository.findByProductNameAndSpecification(
                productdto.getProductName(), productdto.getSpecification());
        Product product = productOptional.get();
        outboundOrder.setProduct(product);
        product.setStockQuantity(product.getStockQuantity() - productdto.getQuantity());
        productRepository.save(product);

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user1 = userDetails.getUser();

        System.out.println("toekn中获取的user"+user1);

        Optional<User> optionalUser = userRepository.findByUsername(user1.getUsername());

        // 检查是否找到用户，如果没找到，抛出异常
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found with username: " + user1));

        System.out.println(user);
        outboundOrder.setUser(user);
        outboundOrder.setOutboundQuantity(productdto.getQuantity());
        outboundOrder.setOutboundDate(new Date());
        outboundOrderRepository.save(outboundOrder);
        System.out.println("出库数据库操作结束");
        return "正在出库";
    }
}
