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

@Composable
fun RestaurantScreen(
    viewModel: IRestaurantViewModel = hiltViewModel<RestaurantViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var postcode by remember { mutableStateOf("") }
    val visibleCount = 10

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
            !uiState.errorMessage.isNullOrBlank() -> {
                Text(
                    text = uiState.errorMessage ?: "Something went wrong.",
                    color = Color(0xFFB00020),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("error_message")
                )
            }
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.testTag("loading_indicator")
                )
            }
            uiState.hasSearched && uiState.restaurants.isEmpty() -> {
                Text(
                    text = "No restaurants found.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("empty_message")
                )
            }
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