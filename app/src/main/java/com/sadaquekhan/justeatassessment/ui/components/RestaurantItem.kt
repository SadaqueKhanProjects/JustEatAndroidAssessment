package com.sadaquekhan.justeatassessment.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**
 * A composable that displays individual restaurant information in a structured layout.
 *
 * Shows name, rating, cuisine list, and full address using the domain model.
 *
 * @param restaurant A [Restaurant] object to be rendered.
 */
@Composable
fun RestaurantItem(restaurant: Restaurant) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = restaurant.name,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Rating: ${restaurant.rating}",
            style = MaterialTheme.typography.bodyMedium
        )

        if (restaurant.cuisines.isNotEmpty()) {
            Text(
                text = "Cuisines: ${restaurant.cuisines.joinToString(", ")}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (restaurant.fullAddress.isNotBlank()) {
            Text(
                text = "Address: ${restaurant.fullAddress}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
