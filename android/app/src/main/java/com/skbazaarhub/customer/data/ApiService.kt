package com.skbazaarhub.customer.data

import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<LoginResponse>>

    @GET("api/v1/products")
    suspend fun getAllProducts(): Response<ApiResponse<List<Product>>>

    @GET("api/v1/products/{id}")
    suspend fun getProductById(@Path("id") id: Long): Response<ApiResponse<Product>>

    @GET("api/v1/products/search")
    suspend fun searchProducts(@Query("q") query: String): Response<ApiResponse<List<Product>>>
}
