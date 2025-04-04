package com.sadaquekhan.justeatassessment.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadaquekhan.justeatassessment.ui.components.RestaurantItem
import com.sadaquekhan.justeatassessment.ui.components.SearchBar
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RestaurantScreen(viewModel: RestaurantViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var postcode by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val visibleCount = 10

    Log.d("RestaurantScreen", "Current UI state contains ${uiState.restaurants.size} restaurants")

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        SearchBar(
            value = postcode,
            onValueChange = {
                postcode = it
                showError = false
            },
            onSearch = {
                if (postcode.trim().length >= 5) {
                    viewModel.loadRestaurants(postcode) // ViewModel handles sanitization
                    showError = false
                } else {
                    showError = true
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            showError -> {
                Text(
                    text = "Please enter a valid UK postcode.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            postcode.isNotBlank() && uiState.restaurants.isEmpty() && !uiState.isLoading -> {
                Text(
                    text = "No restaurants found.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            else -> {
                LazyColumn {
                    items(uiState.restaurants.take(visibleCount)) { restaurant ->
                        Log.d("RestaurantScreen", "Displaying restaurant: ${restaurant.name}")
                        RestaurantItem(restaurant = restaurant)
                    }
                }
            }
        }
    }
}