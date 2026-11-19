package com.skbazaarhub.customer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.skbazaarhub.customer.ui.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    HomeScreen(
                        onProductClick = { productId ->
                            Toast.makeText(this, "Product $productId selected", Toast.LENGTH_SHORT).show()
                        },
                        onCartClick = {
                            Toast.makeText(this, "Opening Cart view", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}
