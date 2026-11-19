package com.skbazaarhub.inventory.service;

import com.skbazaarhub.common.events.InventoryUpdatedEvent;
import com.skbazaarhub.common.exception.InsufficientStockException;
import com.skbazaarhub.common.exception.ResourceNotFoundException;
import com.skbazaarhub.inventory.entity.InventoryEntity;
import com.skbazaarhub.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryEntity getInventoryByVariantId(Long variantId) {
        return inventoryRepository.findByVariantId(variantId)
                .orElseGet(() -> inventoryRepository.save(InventoryEntity.builder()
                        .variantId(variantId)
                        .availableStock(100) // Default initial stock for testing
                        .reservedStock(0)
                        .build()));
    }

    @Transactional
    public InventoryEntity updateStock(Long variantId, int newStock) {
        InventoryEntity inventory = getInventoryByVariantId(variantId);
        inventory.setAvailableStock(newStock);
        inventory = inventoryRepository.save(inventory);

        publishInventoryEvent(inventory);
        return inventory;
    }

    @Transactional
    public boolean reserveStock(Long variantId, int quantity) {
        InventoryEntity inventory = getInventoryByVariantId(variantId);
        if (inventory.getAvailableStock() < quantity) {
            throw new InsufficientStockException("Insufficient stock for variant ID: " + variantId);
        }

        inventory.setAvailableStock(inventory.getAvailableStock() - quantity);
        inventory.setReservedStock(inventory.getReservedStock() + quantity);
        inventoryRepository.save(inventory);

        publishInventoryEvent(inventory);
        return true;
    }

    @Transactional
    public void confirmStockDeduction(Long variantId, int quantity) {
        InventoryEntity inventory = getInventoryByVariantId(variantId);
        inventory.setReservedStock(Math.max(0, inventory.getReservedStock() - quantity));
        inventoryRepository.save(inventory);

        publishInventoryEvent(inventory);
    }

    private void publishInventoryEvent(InventoryEntity inventory) {
        InventoryUpdatedEvent event = InventoryUpdatedEvent.builder()
                .variantId(inventory.getVariantId())
                .availableStock(inventory.getAvailableStock())
                .reservedStock(inventory.getReservedStock())
                .timestamp(LocalDateTime.now())
                .build();
        try {
            kafkaTemplate.send("inventory.updated", inventory.getVariantId().toString(), event);
        } catch (Exception ignored) {}
    }
}
