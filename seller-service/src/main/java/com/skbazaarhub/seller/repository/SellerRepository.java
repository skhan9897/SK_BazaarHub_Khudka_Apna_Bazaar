package com.skbazaarhub.seller.repository;

import com.skbazaarhub.seller.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
    Optional<SellerEntity> findByUserId(Long userId);
    boolean existsByGstin(String gstin);
}
