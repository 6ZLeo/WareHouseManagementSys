package com.example.erp.repository;

import com.example.erp.entity.InboundOrder;
import com.example.erp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundOrderRepository extends JpaRepository<InboundOrder, Long> {
    InboundOrder save(InboundOrder inboundOrder);
}
