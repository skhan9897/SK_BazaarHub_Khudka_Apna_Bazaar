package com.skbazaarhub.order.controller;

import com.skbazaarhub.common.dto.ApiResponse;
import com.skbazaarhub.common.dto.OrderDto;
import com.skbazaarhub.common.enums.OrderStatus;
import com.skbazaarhub.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> createOrder(@RequestHeader("X-User-Id") Long customerId,
                                                              @RequestBody OrderDto dto) {
        return ResponseEntity.ok(ApiResponse.success(orderService.createOrder(customerId, dto), "Order placed successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderById(id)));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getCustomerOrders(@RequestHeader("X-User-Id") Long customerId) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getCustomerOrders(customerId)));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderDto>> updateOrderStatus(@PathVariable("id") Long id, @RequestParam("status") OrderStatus status) {
        return ResponseEntity.ok(ApiResponse.success(orderService.updateOrderStatus(id, status), "Order status updated"));
    }
}
