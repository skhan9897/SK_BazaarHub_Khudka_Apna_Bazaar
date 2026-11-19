package com.skbazaarhub.order.repository;

import com.skbazaarhub.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByOrderNumber(String orderNumber);
    List<OrderEntity> findByCustomerId(Long customerId);
}
