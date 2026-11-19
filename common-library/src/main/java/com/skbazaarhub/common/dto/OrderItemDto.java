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
public class OrderItemDto {
    private Long id;
    private Long sellerId;
    private Long productId;
    private Long variantId;
    private String productName;
    private String variantInfo;
    private String sku;
    private String imageUrl;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal itemTotal;
}
