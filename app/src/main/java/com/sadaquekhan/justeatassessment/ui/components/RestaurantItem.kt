package com.sadaquekhan.justeatassessment.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sadaquekhan.justeatassessment.R
import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**
 * Displays a single restaurant item including logo, name, rating, cuisines, and address.
 *
 * This version uses a hardcoded image URL for testing.
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
        AsyncImage(
            model = "https://images.unsplash.com/photo-1606788075761-0c02e5a93d30?auto=format&fit=crop&w=400&q=80",
            contentDescription = "Direct load",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = restaurant.name,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.testTag("restaurant_name_${restaurant.id}")
        )

        Spacer(modifier = Modifier.height(4.dp))

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

        if (restaurant.cuisines.isNotEmpty()) {
            Text(
                text = "Cuisines: ${restaurant.cuisines.joinToString(", ")}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("restaurant_cuisines_${restaurant.id}")
            )
        }

        if (restaurant.fullAddress.isNotBlank()) {
            Text(
                text = "Address: ${restaurant.fullAddress}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("restaurant_address_${restaurant.id}")
            )
        }
    }
}
