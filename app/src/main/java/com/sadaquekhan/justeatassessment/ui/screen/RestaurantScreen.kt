package com.sadaquekhan.justeatassessment.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.ui.theme.JustEatAndroidAssessmentTheme
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantUiState
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel
import kotlinx.coroutines.flow.MutableStateFlow


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
@Preview(showBackground = true)
@Composable
fun RestaurantScreenPreview() {
    val mockRestaurants = listOf(
        Restaurant(id = "1", name = "Sushi Heaven", rating = 4.8f, cuisineType = "Japanese", eta = "20 mins"),
        Restaurant(id = "2", name = "Pizza Palace", rating = 4.5f, cuisineType = "Italian", eta = "25 mins"),
        Restaurant(id = "3", name = "Curry Corner", rating = 4.2f, cuisineType = "Indian", eta = "30 mins")
    )

    val mockUiState = RestaurantUiState(
        isLoading = false,
        errorMessage = null,
        restaurants = mockRestaurants
    )

    JustEatAndroidAssessmentTheme {
        val fakeViewModel = object : RestaurantViewModel(repository = TODO()) {
            override val uiState = MutableStateFlow(mockUiState)
        }

        RestaurantScreen(viewModel = fakeViewModel)
    }
}
