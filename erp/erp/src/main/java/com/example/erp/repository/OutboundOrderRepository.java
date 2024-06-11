package com.example.erp.repository;

import com.example.erp.entity.InboundOrder;
import com.example.erp.entity.OutboundOrder;
import com.example.erp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundOrderRepository extends JpaRepository<OutboundOrder, Long> {
    OutboundOrder save(OutboundOrder OutboundOrder);
}
