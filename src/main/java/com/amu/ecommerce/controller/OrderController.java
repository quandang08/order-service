 package com.amu.ecommerce.controller;

import com.amu.ecommerce.dto.OrderRequest;
import com.amu.ecommerce.dto.OrderResponse;
import com.amu.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 @RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<Integer> createOrder(
            @RequestBody @Valid OrderRequest request) {

        return ResponseEntity.ok(service.createdOrder(request));
    }

    @GetMapping
     public ResponseEntity<List<OrderResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{order-id}")
     public ResponseEntity<OrderResponse> findById(
             @PathVariable("order-id") Integer orderId
    ){
        return ResponseEntity.ok(service.findById(orderId));
    }
}
