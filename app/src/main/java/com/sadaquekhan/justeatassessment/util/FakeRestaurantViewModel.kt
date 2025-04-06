package com.sadaquekhan.justeatassessment.util

import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.viewmodel.IRestaurantViewModel
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Fake implementation of the ViewModel used for deterministic UI testing.
 * Allows simulation of different screen states like loading, success, empty, or error.
 */
class FakeRestaurantViewModel : IRestaurantViewModel {

    // Backing state for UIState, controlled manually in tests
    private val _uiState = MutableStateFlow(RestaurantUiState())
    override val uiState: StateFlow<RestaurantUiState> = _uiState

    // No-op for loadRestaurants since we manually control state in tests
    override fun loadRestaurants(postcode: String) = Unit

    // --- Helper methods to simulate different UI states ---

    fun setLoadingState() {
        _uiState.value = RestaurantUiState(isLoading = true)
    }

    fun setEmptyState() {
        _uiState.value = RestaurantUiState(
            isEmpty = true,
            hasSearched = true
        )
    }

    fun setSuccessState() {
        _uiState.value = RestaurantUiState(
            restaurants = listOf(
                Restaurant(
                    id = "1",
                    name = "Pizza Place",
                    cuisines = listOf("Italian", "Vegan"),
                    rating = 4.5,
                    address = Address("123 Main Street", "London", "EC1A1BB")
                ),
                Restaurant(
                    id = "2",
                    name = "Burger Joint",
                    cuisines = listOf("American"),
                    rating = 4.0,
                    address = Address("456 Burger St.", "London", "EC1A2BB")
                )
            ),
            isLoading = false,
            isEmpty = false,
            hasSearched = true
        )
    }

    fun setError(message: String) {
        _uiState.value = RestaurantUiState(
            errorMessage = message,
            hasSearched = true
        )
    }
}
