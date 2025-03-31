package com.sadaquekhan.justeatassessment.ui.screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadaquekhan.justeatassessment.domain.model.Restaurant

@Composable
fun RestaurantItem(restaurant: Restaurant) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Display the restaurant name
        Text(
            text = restaurant.name,
            style = MaterialTheme.typography.titleMedium
        )

        // Display joined cuisine types (e.g. "Italian, Pizza")
        Text(
            text = restaurant.cuisines.joinToString(),
            style = MaterialTheme.typography.bodySmall
        )

        // Display the restaurant's rating
        Text(
            text = "Rating: ${restaurant.rating}",
            style = MaterialTheme.typography.bodySmall
        )

        // Display the address of the restaurant
        Text(
            text = restaurant.address,
            style = MaterialTheme.typography.bodySmall
        )
    }
}