package com.sadaquekhan.justeatassessment.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadaquekhan.justeatassessment.ui.components.RestaurantItem
import com.sadaquekhan.justeatassessment.ui.components.SearchBar
import com.sadaquekhan.justeatassessment.viewmodel.IRestaurantViewModel
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel

/**
 * Main screen that manages restaurant search, loading, and result display.
 *
 * Handles 4 UI states:
 * - Error state → shows error message
 * - Loading state → shows loading spinner
 * - Empty result after search → shows "No restaurants found"
 * - Success state → shows up to 10 restaurants
 *
 * @param viewModel The injected [IRestaurantViewModel] (default via Hilt)
 */
@Composable
fun RestaurantScreen(
    viewModel: IRestaurantViewModel = hiltViewModel<RestaurantViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var postcode by remember { mutableStateOf("") }
    val visibleCount = 10 // Limit shown restaurants per spec

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            value = postcode,
            onValueChange = { postcode = it },
            onSearch = { viewModel.loadRestaurants(postcode) },
            modifier = Modifier.testTag("search_component")
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            // Case 1: Error (e.g., timeout, network failure)
            !uiState.errorMessage.isNullOrBlank() -> {
                Text(
                    text = uiState.errorMessage ?: "Something went wrong.",
                    color = Color(0xFFB00020), // Material error red
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("error_message")
                )
            }

            // Case 2: Still loading
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.testTag("loading_indicator")
                )
            }

            // Case 3: No restaurants found after valid search
            uiState.hasSearched && uiState.restaurants.isEmpty() -> {
                Text(
                    text = "No restaurants found.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("empty_message")
                )
            }

            // Case 4: Success → Display restaurant list
            else -> {
                LazyColumn(
                    modifier = Modifier.testTag("restaurant_list")
                ) {
                    items(uiState.restaurants.take(visibleCount)) { restaurant ->
                        RestaurantItem(
                            restaurant = restaurant,
                            modifier = Modifier.testTag("restaurant_item_${restaurant.id}")
                        )
                    }
                }
            }
        }
    }
}
