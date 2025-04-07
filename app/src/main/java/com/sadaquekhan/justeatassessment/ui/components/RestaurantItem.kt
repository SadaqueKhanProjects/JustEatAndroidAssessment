package com.sadaquekhan.justeatassessment.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**
 * Displays a single restaurant's information in a standardized format.
 *
 * @param restaurant The domain model containing sanitized restaurant data
 * @param modifier Compose modifier for layout adjustments
 *
 * Behavior:
 * - Always shows name
 * - Shows "Not rated yet" for null ratings (grayed out)
 * - Shows numeric rating for 0.0+ ratings
 * - Conditionally shows cuisines/address if available
 */
@Composable
fun RestaurantItem(
    restaurant: Restaurant,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("restaurant_item_${restaurant.id}")
    ) {
        // Required field - always visible
        Text(
            text = restaurant.name,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.testTag("restaurant_name_${restaurant.id}")
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Rating display logic
        when (restaurant.rating) {
            null -> Text(
                text = "Rating: Not rated yet",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.testTag("restaurant_rating_${restaurant.id}")
            )
            else -> Text(
                text = "Rating: ${"%.1f".format(restaurant.rating)}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("restaurant_rating_${restaurant.id}")
            )
        }

        // Optional: Cuisine list
        if (restaurant.cuisines.isNotEmpty()) {
            Text(
                text = "Cuisines: ${restaurant.cuisines.joinToString(", ")}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("restaurant_cuisines_${restaurant.id}")
            )
        }

        // Optional: Address
        if (restaurant.fullAddress.isNotBlank()) {
            Text(
                text = "Address: ${restaurant.fullAddress}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("restaurant_address_${restaurant.id}")
            )
        }
    }
}