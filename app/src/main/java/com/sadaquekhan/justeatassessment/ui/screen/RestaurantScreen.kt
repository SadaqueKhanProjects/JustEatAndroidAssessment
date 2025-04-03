package com.sadaquekhan.justeatassessment.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.sadaquekhan.justeatassessment.ui.screen.components.RestaurantItem
import com.sadaquekhan.justeatassessment.ui.screen.components.SearchBar
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel

/**
 * Main screen for displaying restaurant search results.
 * Handles rendering of:
 * - Search bar
 * - Validation errors
 * - Loading spinner
 * - Error messages (network, timeout, unknown)
 * - Empty results message
 * - Top 10 restaurant cards
 */
@Composable
fun RestaurantScreen() {
    val viewModel: RestaurantViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var postcode by remember { mutableStateOf("") }
    var showValidationError by remember { mutableStateOf(false) }
    val visibleCount = 10

    Log.d("RestaurantScreen", "UI contains ${uiState.restaurants.size} restaurants")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            value = postcode,
            onValueChange = {
                postcode = it
                showValidationError = false
            },
            onSearch = {
                if (postcode.trim().length >= 5) {
                    viewModel.loadRestaurants(postcode)
                    showValidationError = false
                } else {
                    showValidationError = true
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Manual input error
        if (showValidationError) {
            Text(
                text = "Please enter a valid UK postcode.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Display network/backend error
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage ?: "Something went wrong.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Display no results if search valid but returned nothing
        if (postcode.isNotBlank() && uiState.restaurants.isEmpty()
            && !uiState.isLoading && uiState.errorMessage == null
        ) {
            Text(
                text = "No restaurants found.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Show spinner
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        // Render results
        if (uiState.restaurants.isNotEmpty()) {
            LazyColumn {
                items(uiState.restaurants.take(visibleCount)) { restaurant ->
                    Log.d("RestaurantScreen", "Rendering: ${restaurant.name}")
                    RestaurantItem(restaurant = restaurant)
                }
            }
        }
    }
}