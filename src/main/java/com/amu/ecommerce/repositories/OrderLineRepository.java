package com.amu.ecommerce.repositories;

import com.amu.ecommerce.dto.OrderLineResponse;
import com.amu.ecommerce.entities.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}
