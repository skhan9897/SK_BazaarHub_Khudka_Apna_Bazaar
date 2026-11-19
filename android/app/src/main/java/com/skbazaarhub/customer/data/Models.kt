package com.skbazaarhub.customer.data

import java.math.BigDecimal

data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?
)

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val basePrice: BigDecimal,
    val mrp: BigDecimal,
    val mainImageUrl: String?,
    val rating: Double?,
    val categoryName: String?
)

data class CartItem(
    val id: Long,
    val productId: Long,
    val variantId: Long,
    val productName: String,
    val imageUrl: String?,
    val price: BigDecimal,
    var quantity: Int
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
    val email: String,
    val name: String
)
