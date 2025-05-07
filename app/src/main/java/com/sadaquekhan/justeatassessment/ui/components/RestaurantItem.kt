package com.sadaquekhan.justeatassessment.ui.components

import android.graphics.Insets.add
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
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
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
        val context = LocalContext.current
        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(ImageDecoderDecoder.Factory()) // For API 28+
                add(GifDecoder.Factory())          // Fallback for older APIs
            }
            .build()

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(restaurant.logoUrl)
                .crossfade(true)
                .build(),
            imageLoader = imageLoader,
            contentDescription = "${restaurant.name} logo",
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.ic_justeat_placeholder),
            error = painterResource(id = R.drawable.ic_justeat_placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .testTag("restaurant_logo_${restaurant.id}")
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
