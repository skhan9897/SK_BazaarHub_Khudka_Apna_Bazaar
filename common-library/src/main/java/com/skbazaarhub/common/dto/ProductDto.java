package com.skbazaarhub.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private Long sellerId;
    private String sellerBusinessName;
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private String name;
    private String slug;
    private String description;
    private BigDecimal basePrice;
    private BigDecimal mrp;
    private String mainImageUrl;
    private List<String> galleryImages;
    private boolean active;
    private boolean approved;
    private Double rating;
    private Integer reviewCount;
    private List<ProductVariantDto> variants;
}
