package com.skbazaarhub.customer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skbazaarhub.customer.data.Product
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProductClick: (Long) -> Unit,
    onCartClick: () -> Unit
) {
    val sampleProducts = listOf(
        Product(1L, "Wireless Headphones", "High quality ANC headphones", BigDecimal("4999.00"), BigDecimal("9999.00"), null, 4.8, "Electronics"),
        Product(2L, "Smart Watch Pro", "Fitness watch with AMOLED display", BigDecimal("2999.00"), BigDecimal("5999.00"), null, 4.6, "Wearables"),
        Product(3L, "Ergonomic Chair", "Comfortable office mesh chair", BigDecimal("7499.00"), BigDecimal("14999.00"), null, 4.9, "Furniture"),
        Product(4L, "Bluetooth Speaker", "Portable waterproof speaker", BigDecimal("1999.00"), BigDecimal("3999.00"), null, 4.5, "Audio")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SK BazaarHub", fontWeight = FontWeight.Bold, color = Color(0xFF5B35E8)) },
                actions = {
                    TextButton(onClick = onCartClick) {
                        Text("Cart (2)", fontWeight = FontWeight.Bold, color = Color(0xFF5B35E8))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF5B35E8))
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    Text("Multi-Vendor Marketplace", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Shop synced deals on Android & Web", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Featured Products", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sampleProducts) { product ->
                    ProductCard(product = product, onClick = { onProductClick(product.id) })
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFFF6F7FB), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(product.name.take(1), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5B35E8))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1)
            Text("₹${product.basePrice}", color = Color(0xFF5B35E8), fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
        }
    }
}
