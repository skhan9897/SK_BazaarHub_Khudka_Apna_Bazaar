package com.skbazaarhub.order.service;

import com.skbazaarhub.common.dto.OrderDto;
import com.skbazaarhub.common.dto.OrderItemDto;
import com.skbazaarhub.common.enums.OrderStatus;
import com.skbazaarhub.common.enums.PaymentMethod;
import com.skbazaarhub.common.enums.PaymentStatus;
import com.skbazaarhub.common.events.OrderCancelledEvent;
import com.skbazaarhub.common.events.OrderConfirmedEvent;
import com.skbazaarhub.common.events.OrderCreatedEvent;
import com.skbazaarhub.common.exception.ResourceNotFoundException;
import com.skbazaarhub.order.entity.OrderEntity;
import com.skbazaarhub.order.entity.OrderItemEntity;
import com.skbazaarhub.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public OrderDto createOrder(Long customerId, OrderDto dto) {
        String orderNumber = "SKB-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);

        OrderEntity order = OrderEntity.builder()
                .orderNumber(orderNumber)
                .customerId(customerId)
                .shippingAddressId(dto.getShippingAddress() != null ? dto.getShippingAddress().getId() : 1L)
                .totalAmount(dto.getTotalAmount())
                .discountAmount(dto.getDiscountAmount() != null ? dto.getDiscountAmount() : BigDecimal.ZERO)
                .taxAmount(dto.getTaxAmount() != null ? dto.getTaxAmount() : BigDecimal.ZERO)
                .shippingFee(dto.getShippingFee() != null ? dto.getShippingFee() : BigDecimal.ZERO)
                .finalAmount(dto.getFinalAmount())
                .orderStatus(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.INITIATED)
                .paymentMethod(dto.getPaymentMethod() != null ? dto.getPaymentMethod() : PaymentMethod.RAZORPAY_UPI)
                .build();

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            List<OrderItemEntity> items = new ArrayList<>();
            for (OrderItemDto itemDto : dto.getItems()) {
                items.add(OrderItemEntity.builder()
                        .order(order)
                        .sellerId(itemDto.getSellerId() != null ? itemDto.getSellerId() : 1L)
                        .productId(itemDto.getProductId())
                        .variantId(itemDto.getVariantId())
                        .productName(itemDto.getProductName())
                        .sku(itemDto.getSku() != null ? itemDto.getSku() : "SKU-" + itemDto.getVariantId())
                        .price(itemDto.getPrice())
                        .quantity(itemDto.getQuantity())
                        .itemTotal(itemDto.getItemTotal())
                        .build());
            }
            order.setItems(items);
        }

        order = orderRepository.save(order);

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerId(customerId)
                .finalAmount(order.getFinalAmount())
                .timestamp(LocalDateTime.now())
                .build();

        try {
            kafkaTemplate.send("order.created", order.getOrderNumber(), event);
        } catch (Exception ignored) {}

        return mapToDto(order);
    }

    public OrderDto getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
        return mapToDto(order);
    }

    public List<OrderDto> getCustomerOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDto updateOrderStatus(Long orderId, OrderStatus status) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
        order.setOrderStatus(status);

        if (status == OrderStatus.CONFIRMED) {
            order.setPaymentStatus(PaymentStatus.SUCCESS);
            try {
                kafkaTemplate.send("order.confirmed", order.getOrderNumber(),
                        OrderConfirmedEvent.builder()
                                .orderId(order.getId())
                                .orderNumber(order.getOrderNumber())
                                .customerId(order.getCustomerId())
                                .amount(order.getFinalAmount())
                                .timestamp(LocalDateTime.now())
                                .build());
            } catch (Exception ignored) {}
        } else if (status == OrderStatus.CANCELLED) {
            try {
                kafkaTemplate.send("order.cancelled", order.getOrderNumber(),
                        OrderCancelledEvent.builder()
                                .orderId(order.getId())
                                .orderNumber(order.getOrderNumber())
                                .customerId(order.getCustomerId())
                                .reason("Cancelled by user/system")
                                .timestamp(LocalDateTime.now())
                                .build());
            } catch (Exception ignored) {}
        }

        return mapToDto(orderRepository.save(order));
    }

    private OrderDto mapToDto(OrderEntity entity) {
        List<OrderItemDto> itemDtos = entity.getItems().stream()
                .map(i -> OrderItemDto.builder()
                        .id(i.getId())
                        .sellerId(i.getSellerId())
                        .productId(i.getProductId())
                        .variantId(i.getVariantId())
                        .productName(i.getProductName())
                        .sku(i.getSku())
                        .price(i.getPrice())
                        .quantity(i.getQuantity())
                        .itemTotal(i.getItemTotal())
                        .build())
                .collect(Collectors.toList());

        return OrderDto.builder()
                .id(entity.getId())
                .orderNumber(entity.getOrderNumber())
                .customerId(entity.getCustomerId())
                .totalAmount(entity.getTotalAmount())
                .discountAmount(entity.getDiscountAmount())
                .taxAmount(entity.getTaxAmount())
                .shippingFee(entity.getShippingFee())
                .finalAmount(entity.getFinalAmount())
                .orderStatus(entity.getOrderStatus())
                .paymentStatus(entity.getPaymentStatus())
                .paymentMethod(entity.getPaymentMethod())
                .trackingNumber(entity.getTrackingNumber())
                .items(itemDtos)
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
