package com.sadaquekhan.justeatassessment.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel
import androidx.hilt.navigation.compose.hiltViewModel

// RestaurantScreen.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(viewModel: RestaurantViewModel = hiltViewModel()) {
    // Collect the current UI state from ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Scaffold allows you to add top bars/snackbars later if needed
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Restaurants") })
        }
    ) { paddingValues ->
        // LazyColumn efficiently renders large lists in a scrollable column
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Iterate over the restaurant list from state
            items(uiState.restaurants.take(10)) { restaurant ->
                RestaurantItem(restaurant = restaurant)
                Spacer(modifier = Modifier.height(8.dp)) // Spacing between items
            }
        }
    }
}

@Composable
fun RestaurantItem(restaurant: Restaurant) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = restaurant.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Cuisine: ${restaurant.cuisines}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(text = "Rating: ${restaurant.rating}", style = MaterialTheme.typography.bodySmall)
            Text(
                text = "Address: ${restaurant.address}",
                style = MaterialTheme.typography.bodySmall
            ) // 🔁 Replaces ETA
        }
    }
}