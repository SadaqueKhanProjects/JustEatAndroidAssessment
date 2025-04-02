package com.sadaquekhan.justeatassessment.ui.screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sadaquekhan.justeatassessment.domain.model.Restaurant

@Composable
fun RestaurantItem(restaurant: Restaurant) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Name
        Text(
            text = restaurant.name,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Rating
        Text(
            text = "Rating: ${restaurant.rating}",
            style = MaterialTheme.typography.bodyMedium
        )

        // Cuisines
        if (restaurant.cuisines.isNotEmpty()) {
            Text(
                text = "Cuisines: ${restaurant.cuisines.joinToString(separator = ", ")}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Address (now using pre-computed fullAddress)
        if (restaurant.fullAddress.isNotBlank()) {
            Text(
                text = "Address: ${restaurant.fullAddress}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}