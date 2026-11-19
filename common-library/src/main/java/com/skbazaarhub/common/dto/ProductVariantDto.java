package com.skbazaarhub.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantDto {
    private Long id;
    private String sku;
    private String name;
    private String color;
    private String size;
    private BigDecimal price;
    private BigDecimal mrp;
    private Integer stockQuantity;
    private String imageUrl;
}
