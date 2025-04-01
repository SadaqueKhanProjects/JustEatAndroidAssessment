package com.sadaquekhan.justeatassessment.ui.screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadaquekhan.justeatassessment.domain.model.Restaurant

@Composable
fun RestaurantItem(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = restaurant.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Rating: ${restaurant.rating}")
            Text(text = "Cuisines: ${restaurant.cuisines.joinToString(", ")}")
            Text(text = "Address: ${restaurant.addressLine}")
        }
    }
}