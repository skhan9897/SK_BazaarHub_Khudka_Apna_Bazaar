package com.skbazaarhub.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdatedEvent {
    private Long variantId;
    private Long productId;
    private String sku;
    private Integer availableStock;
    private Integer reservedStock;
    private LocalDateTime timestamp;
}
