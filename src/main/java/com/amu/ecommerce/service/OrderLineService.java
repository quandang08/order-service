package com.amu.ecommerce.service;

import com.amu.ecommerce.dto.OrderLineRequest;
import com.amu.ecommerce.mapper.OrderLineMapper;
import com.amu.ecommerce.repositories.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest request) {
        var order = mapper.toOrderLine(request);
        return repository.save(order).getId();
    }

}
