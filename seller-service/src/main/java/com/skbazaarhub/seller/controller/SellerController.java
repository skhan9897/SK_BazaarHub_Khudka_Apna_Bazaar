package com.skbazaarhub.seller.controller;

import com.skbazaarhub.common.dto.ApiResponse;
import com.skbazaarhub.common.dto.SellerRegistrationDto;
import com.skbazaarhub.seller.entity.SellerEntity;
import com.skbazaarhub.seller.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SellerEntity>> registerSeller(@RequestHeader("X-User-Id") Long userId,
                                                                      @Valid @RequestBody SellerRegistrationDto dto) {
        SellerEntity seller = sellerService.registerSeller(userId, dto);
        return ResponseEntity.ok(ApiResponse.success(seller, "Seller registered successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<SellerEntity>> getMySellerProfile(@RequestHeader("X-User-Id") Long userId) {
        SellerEntity seller = sellerService.getSellerByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(seller));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SellerEntity>> getSellerById(@PathVariable("id") Long id) {
        SellerEntity seller = sellerService.getSellerById(id);
        return ResponseEntity.ok(ApiResponse.success(seller));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SellerEntity>>> getAllSellers() {
        List<SellerEntity> sellers = sellerService.getAllSellers();
        return ResponseEntity.ok(ApiResponse.success(sellers));
    }

    @PutMapping("/{id}/approve-kyc")
    public ResponseEntity<ApiResponse<SellerEntity>> approveKyc(@PathVariable("id") Long id,
                                                                 @RequestParam("adminUsername") String adminUsername) {
        SellerEntity seller = sellerService.approveKyc(id, adminUsername);
        return ResponseEntity.ok(ApiResponse.success(seller, "Seller KYC approved successfully"));
    }
}
