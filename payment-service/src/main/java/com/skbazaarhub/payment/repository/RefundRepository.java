package com.skbazaarhub.payment.repository;

import com.skbazaarhub.payment.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepository extends JpaRepository<RefundEntity, Long> {
    List<RefundEntity> findByOrderId(Long orderId);
}
