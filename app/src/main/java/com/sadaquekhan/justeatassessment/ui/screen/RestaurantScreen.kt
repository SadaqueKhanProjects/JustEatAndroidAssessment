package com.sadaquekhan.justeatassessment.ui.screen

import android.util.Log
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
 * RestaurantScreen composable displays the UI for the main screen.
 * It allows the user to input a postcode, fetches restaurant data,
 * and displays loading, error, empty, or success states.
 *
 * @param viewModel The ViewModel responsible for managing UI state and business logic.
 * Default is injected using hiltViewModel().
 */
@Composable
fun RestaurantScreen(viewModel: IRestaurantViewModel = hiltViewModel<RestaurantViewModel>()) {

    // Collecting the UI state from the ViewModel and observing changes.
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Local state for the postcode input
    var postcode by remember { mutableStateOf("") }

    // Defining how many items to display in the list
    val visibleCount = 10

    // Column layout to arrange the UI components
    Column(
        modifier = Modifier
            .fillMaxSize()  // Fill the available space
            .padding(16.dp) // Apply padding around the components
    ) {
        // Search bar for entering the postcode
        SearchBar(
            value = postcode,
            onValueChange = { postcode = it },
            onSearch = { viewModel.loadRestaurants(postcode) }  // Trigger loading restaurants based on postcode
        )

        // Spacer to create space between the search bar and the list
        Spacer(modifier = Modifier.height(16.dp))

        // Handle different UI states (Loading, Error, Empty, Success)
        when {
            // If there is an error message, show it
            !uiState.errorMessage.isNullOrBlank() -> {
                Text(
                    text = uiState.errorMessage ?: "Something went wrong.",  // Show the error message or default
                    color = Color(0xFFB00020),  // Red color for errors
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            // If loading, show the loading indicator
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.testTag("loading_indicator"))
            }
            // If no restaurants are found, display a message
            uiState.hasSearched && uiState.restaurants.isEmpty() -> {
                Text(
                    text = "No restaurants found.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            // If restaurants are available, show them in a list
            else -> {
                LazyColumn {
                    items(uiState.restaurants.take(visibleCount)) { restaurant ->
                        RestaurantItem(restaurant = restaurant)
                    }
                }
            }
        }
    }
}