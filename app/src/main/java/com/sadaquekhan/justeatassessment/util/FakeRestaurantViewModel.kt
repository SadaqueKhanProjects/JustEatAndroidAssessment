package com.sadaquekhan.justeatassessment.util

import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.viewmodel.IRestaurantViewModel
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FakeRestaurantViewModel : IRestaurantViewModel {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val _uiState = MutableStateFlow(RestaurantUiState())
    override val uiState: StateFlow<RestaurantUiState> = _uiState

    private fun isValidPostcode(postcode: String): Boolean {
        return postcode.matches("^[A-Z]{1,2}[0-9][0-9A-Z]? ?[0-9][A-Z]{2}$".toRegex())
    }

    override fun loadRestaurants(postcode: String) {
        _uiState.value = _uiState.value.copy(
            hasSearched = true,
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch {
            delay(100) // Simulate network delay

            _uiState.value = when {
                postcode.isBlank() -> {
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Postcode cannot be empty"
                    )
                }
                !isValidPostcode(postcode) -> {
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Invalid UK postcode format"
                    )
                }
                else -> {
                    _uiState.value.copy(
                        isLoading = false,
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
                        isEmpty = false
                    )
                }
            }
        }
    }

    // Helper methods for test setup
    fun setLoadingState() {
        _uiState.value = RestaurantUiState(isLoading = true)
    }

    fun setEmptyState() {
        _uiState.value = RestaurantUiState(
            isEmpty = true,
            hasSearched = true
        )
    }

    // Update setSuccessState to accept custom restaurants
    fun setSuccessState(restaurants: List<Restaurant>? = null) {
        _uiState.value = RestaurantUiState(
            restaurants = restaurants ?: listOf(
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