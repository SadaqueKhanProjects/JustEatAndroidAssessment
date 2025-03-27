package com.sadaquekhan.justeatassessment.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel
import com.sadaquekhan.justeatassessment.domain.model.Restaurant

import androidx.compose.foundation.lazy.items


@Composable
fun RestaurantScreen(viewModel: RestaurantViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            CircularProgressIndicator()
        }
        uiState.errorMessage != null -> {
            Text("Error: ${uiState.errorMessage}")
        }
        else -> {
            LazyColumn {
                items(uiState.restaurants) { restaurant ->
                    Text(text = restaurant.name)
                }
            }
        }
    }
}
