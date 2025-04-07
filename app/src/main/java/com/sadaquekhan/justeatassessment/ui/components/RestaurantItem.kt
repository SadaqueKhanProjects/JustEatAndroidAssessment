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
 * Displays the visual layout for a single restaurant entry.
 *
 * This UI component renders key restaurant data such as:
 * - Name
 * - Rating (or placeholder if not rated)
 * - Cuisine list (if available)
 * - Formatted address
 *
 * @param restaurant The domain model containing sanitized restaurant information
 * @param modifier Optional [Modifier] for layout or testing
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
        Text(
            text = restaurant.name,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.testTag("restaurant_name_${restaurant.id}")
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Display rating: show placeholder if not rated
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

        // Show cuisines only if available
        if (restaurant.cuisines.isNotEmpty()) {
            Text(
                text = "Cuisines: ${restaurant.cuisines.joinToString(", ")}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("restaurant_cuisines_${restaurant.id}")
            )
        }

        // Show address only if valid and not blank
        if (restaurant.fullAddress.isNotBlank()) {
            Text(
                text = "Address: ${restaurant.fullAddress}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("restaurant_address_${restaurant.id}")
            )
        }
    }
}
