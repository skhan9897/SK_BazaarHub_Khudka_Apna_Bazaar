package com.skbazaarhub.product.service;

import com.skbazaarhub.common.dto.ProductDto;
import com.skbazaarhub.common.dto.ProductVariantDto;
import com.skbazaarhub.common.events.ProductCreatedEvent;
import com.skbazaarhub.common.exception.ResourceNotFoundException;
import com.skbazaarhub.product.entity.CategoryEntity;
import com.skbazaarhub.product.entity.ProductEntity;
import com.skbazaarhub.product.entity.ProductVariantEntity;
import com.skbazaarhub.product.repository.CategoryRepository;
import com.skbazaarhub.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return mapToDto(product);
    }

    public List<ProductDto> searchProducts(String query) {
        return productRepository.searchProducts(query).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getProductsBySeller(Long sellerId) {
        return productRepository.findBySellerId(sellerId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto createProduct(Long sellerId, ProductDto dto) {
        CategoryEntity category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
        }

        ProductEntity product = ProductEntity.builder()
                .sellerId(sellerId)
                .category(category)
                .name(dto.getName())
                .slug(dto.getName().toLowerCase().replaceAll("[^a-z0-9]", "-") + "-" + System.currentTimeMillis())
                .description(dto.getDescription())
                .basePrice(dto.getBasePrice())
                .mrp(dto.getMrp())
                .mainImageUrl(dto.getMainImageUrl())
                .active(true)
                .approved(true)
                .build();

        if (dto.getVariants() != null && !dto.getVariants().isEmpty()) {
            List<ProductVariantEntity> variants = new ArrayList<>();
            for (ProductVariantDto vDto : dto.getVariants()) {
                variants.add(ProductVariantEntity.builder()
                        .product(product)
                        .sku(vDto.getSku() != null ? vDto.getSku() : "SKU-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000))
                        .name(vDto.getName())
                        .color(vDto.getColor())
                        .size(vDto.getSize())
                        .price(vDto.getPrice() != null ? vDto.getPrice() : dto.getBasePrice())
                        .mrp(vDto.getMrp() != null ? vDto.getMrp() : dto.getMrp())
                        .imageUrl(vDto.getImageUrl() != null ? vDto.getImageUrl() : dto.getMainImageUrl())
                        .build());
            }
            product.setVariants(variants);
        }

        product = productRepository.save(product);

        ProductCreatedEvent event = ProductCreatedEvent.builder()
                .productId(product.getId())
                .sellerId(sellerId)
                .name(product.getName())
                .categoryId(category != null ? category.getId() : null)
                .basePrice(product.getBasePrice())
                .timestamp(LocalDateTime.now())
                .build();
        try {
            kafkaTemplate.send("product.created", product.getId().toString(), event);
        } catch (Exception ignored) {}

        return mapToDto(product);
    }

    private ProductDto mapToDto(ProductEntity entity) {
        List<ProductVariantDto> variantDtos = entity.getVariants().stream()
                .map(v -> ProductVariantDto.builder()
                        .id(v.getId())
                        .sku(v.getSku())
                        .name(v.getName())
                        .color(v.getColor())
                        .size(v.getSize())
                        .price(v.getPrice())
                        .mrp(v.getMrp())
                        .imageUrl(v.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        return ProductDto.builder()
                .id(entity.getId())
                .sellerId(entity.getSellerId())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : null)
                .name(entity.getName())
                .slug(entity.getSlug())
                .description(entity.getDescription())
                .basePrice(entity.getBasePrice())
                .mrp(entity.getMrp())
                .mainImageUrl(entity.getMainImageUrl())
                .active(entity.isActive())
                .approved(entity.isApproved())
                .rating(entity.getRating())
                .reviewCount(entity.getReviewCount())
                .variants(variantDtos)
                .build();
    }
}
