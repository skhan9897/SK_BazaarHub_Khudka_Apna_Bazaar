package com.skbazaarhub.product.repository;

import com.skbazaarhub.product.entity.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {
    Optional<ProductVariantEntity> findBySku(String sku);
}
