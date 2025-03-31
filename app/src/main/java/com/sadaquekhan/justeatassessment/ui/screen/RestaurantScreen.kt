package com.sadaquekhan.justeatassessment.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sadaquekhan.justeatassessment.ui.screen.components.RestaurantItem
import com.sadaquekhan.justeatassessment.ui.screen.components.SearchBar
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(viewModel: RestaurantViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    var postcode by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var visibleCount by remember { mutableStateOf(10) }

    val ukPostcodeRegex = Regex("^[A-Z]{1,2}\\d[A-Z\\d]? ?\\d[A-Z]{2}\$", RegexOption.IGNORE_CASE)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Restaurants") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Reusable SearchBar component
            SearchBar(
                postcode = postcode,
                onPostcodeChange = { postcode = it },
                onSearch = {
                    focusManager.clearFocus()
                    if (ukPostcodeRegex.matches(postcode.trim())) {
                        viewModel.loadRestaurants(postcode.trim().uppercase())
                        visibleCount = 10
                        showError = false
                    } else {
                        showError = true
                    }
                },
                showError = showError
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

                postcode.isNotBlank() && uiState.restaurants.isEmpty() -> {
                    Text(
                        text = "No restaurants found.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                else -> {
                    LazyColumn {
                        items(uiState.restaurants.take(visibleCount)) { restaurant ->
                            RestaurantItem(restaurant = restaurant)
                        }

                        // Show load more button if more restaurants are available
                        if (uiState.restaurants.size > visibleCount) {
                            item {
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = { visibleCount += 10 },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    Text("Load More")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}