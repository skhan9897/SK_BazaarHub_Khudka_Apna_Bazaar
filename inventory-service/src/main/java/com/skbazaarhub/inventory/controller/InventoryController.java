package com.skbazaarhub.inventory.controller;

import com.skbazaarhub.common.dto.ApiResponse;
import com.skbazaarhub.inventory.entity.InventoryEntity;
import com.skbazaarhub.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/variant/{variantId}")
    public ResponseEntity<ApiResponse<InventoryEntity>> getInventory(@PathVariable("variantId") Long variantId) {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.getInventoryByVariantId(variantId)));
    }

    @PutMapping("/variant/{variantId}")
    public ResponseEntity<ApiResponse<InventoryEntity>> updateStock(@PathVariable("variantId") Long variantId, @RequestParam("stock") int stock) {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.updateStock(variantId, stock), "Stock updated successfully"));
    }

    @PostMapping("/reserve")
    public ResponseEntity<ApiResponse<Boolean>> reserveStock(@RequestParam("variantId") Long variantId, @RequestParam("quantity") int quantity) {
        boolean reserved = inventoryService.reserveStock(variantId, quantity);
        return ResponseEntity.ok(ApiResponse.success(reserved, "Stock reserved successfully"));
    }
}
