package com.amu.ecommerce.repositories;

import com.amu.ecommerce.entities.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
}
