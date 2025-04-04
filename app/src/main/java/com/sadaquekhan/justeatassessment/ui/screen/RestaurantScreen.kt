package com.sadaquekhan.justeatassessment.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadaquekhan.justeatassessment.ui.components.RestaurantItem
import com.sadaquekhan.justeatassessment.ui.components.SearchBar
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel

/**
 * Main screen composable that renders the search bar, error states,
 * loading indicator, and a scrollable list of restaurants.
 *
 * ViewModel is injected via Hilt. UI state is observed via StateFlow.
 */
@Composable
fun RestaurantScreen() {
    val viewModel: RestaurantViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var postcode by remember { mutableStateOf("") }
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
            },
            onSearch = {
                viewModel.loadRestaurants(postcode)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            !uiState.errorMessage.isNullOrBlank() -> {
                Text(
                    text = uiState.errorMessage ?: "Something went wrong.",
                    color = Color(0xFFB00020),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }

            uiState.hasSearched && uiState.restaurants.isEmpty() -> {
                Text(
                    text = "No restaurants found.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            else -> {
                LazyColumn {
                    items(uiState.restaurants.take(visibleCount)) { restaurant ->
                        Log.d("RestaurantScreen", "Rendering: ${restaurant.name}")
                        RestaurantItem(restaurant = restaurant)
                    }
                }
            }
        }
    }
}