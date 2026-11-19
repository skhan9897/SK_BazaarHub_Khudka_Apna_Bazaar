package com.skbazaarhub.product.controller;

import com.skbazaarhub.common.dto.ApiResponse;
import com.skbazaarhub.common.dto.ProductDto;
import com.skbazaarhub.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts() {
        return ResponseEntity.ok(ApiResponse.success(productService.getAllProducts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductDto>>> searchProducts(@RequestParam String q) {
        return ResponseEntity.ok(ApiResponse.success(productService.searchProducts(q)));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsBySeller(@PathVariable Long sellerId) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductsBySeller(sellerId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestHeader("X-Seller-Id") Long sellerId,
                                                                  @RequestBody ProductDto dto) {
        return ResponseEntity.ok(ApiResponse.success(productService.createProduct(sellerId, dto), "Product created successfully"));
    }
}
