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
            Text(
                text = restaurant.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Cuisine: ${restaurant.cuisineType}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Rating: ${restaurant.rating}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Address: ${restaurant.address}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

// Preview-only UI showing mocked restaurants — safe to delete after development
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreenPreview() {
    val mockRestaurants = listOf(
        Restaurant(
            id = "1",
            name = "Mock Pizza Palace",
            cuisineType = "Italian",
            rating = 4.5,
            address = "123 Mock Street"
        ),
        Restaurant(
            id = "2",
            name = "Sushi Mock House",
            cuisineType = "Japanese",
            rating = 4.8,
            address = "456 Preview Lane"
        ),
        Restaurant(
            id = "3",
            name = "Burger Byte",
            cuisineType = "American",
            rating = 4.3,
            address = "789 Burger Blvd"
        ),
        Restaurant(
            id = "4",
            name = "Curry Kingdom",
            cuisineType = "Indian",
            rating = 4.7,
            address = "321 Spice Road"
        ),
        Restaurant(
            id = "5",
            name = "Dragon Dumplings",
            cuisineType = "Chinese",
            rating = 4.6,
            address = "88 Lantern Ave"
        ),
        Restaurant(
            id = "6",
            name = "Taco Mocko",
            cuisineType = "Mexican",
            rating = 4.4,
            address = "246 Salsa Street"
        ),
        Restaurant(
            id = "7",
            name = "Mock Thai Express",
            cuisineType = "Thai",
            rating = 4.2,
            address = "135 Coconut Grove"
        ),
        Restaurant(
            id = "8",
            name = "Green Garden",
            cuisineType = "Vegetarian",
            rating = 4.1,
            address = "101 Herb Lane"
        ),
        Restaurant(
            id = "9",
            name = "Mock Mediterranean",
            cuisineType = "Greek",
            rating = 4.5,
            address = "77 Olive Way"
        ),
        Restaurant(
            id = "10",
            name = "BBQ Bonanza",
            cuisineType = "Barbecue",
            rating = 4.6,
            address = "654 Smokehouse Drive"
        )
    )

    // Directly pass mock list to UI without ViewModel
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Preview: Restaurants") })
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(mockRestaurants) { restaurant ->
                RestaurantItem(restaurant = restaurant)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

// Enables Android Studio Preview window
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun RestaurantScreenPreviewWrapper() {
    com.sadaquekhan.justeatassessment.ui.theme.JustEatAndroidAssessmentTheme {
        RestaurantScreenPreview()
    }
}
