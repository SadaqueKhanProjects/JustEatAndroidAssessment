package com.sadaquekhan.justeatassessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadaquekhan.justeatassessment.data.repository.IRestaurantRepository
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.domain.result.RestaurantResult
import com.sadaquekhan.justeatassessment.util.logging.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing restaurant data and screen state.
 */
@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: IRestaurantRepository,
    private val logger: Logger
) : ViewModel(), IRestaurantViewModel {

    private val _uiState = MutableStateFlow(RestaurantUiState())
    override val uiState: StateFlow<RestaurantUiState> = _uiState

    override fun loadRestaurants(rawPostcode: String) {
        val sanitizedPostcode = rawPostcode
            .trim()
            .replace("\\s+".toRegex(), "")
            .uppercase()

        when {
            rawPostcode.isBlank() -> {
                setError("Please enter a postcode")
                return
            }

            !isValidPostcode(sanitizedPostcode) -> {
                setError("Invalid UK postcode format")
                return
            }

            else -> {
                logger.debug("RestaurantViewModel", "Loading restaurants for postcode: $sanitizedPostcode")
                viewModelScope.launch {
                    setLoading()

                    when (val result = repository.getRestaurants(sanitizedPostcode)) {
                        is RestaurantResult.Success -> setSuccess(result.data)
                        is RestaurantResult.NetworkError -> setError(result.message)
                        is RestaurantResult.Timeout -> setError(result.message)
                        is RestaurantResult.UnknownError -> setError(result.message)
                    }
                }
            }
        }
    }

    // --- State Helper Methods ---

    private fun setLoading() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null,
            hasSearched = true
        )
    }

    private fun setError(message: String) {
        logger.error("RestaurantViewModel", "Error: $message")
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            errorMessage = message,
            hasSearched = true
        )
    }

    private fun setSuccess(data: List<Restaurant>) {
        _uiState.value = _uiState.value.copy(
            restaurants = data,
            isLoading = false,
            isEmpty = data.isEmpty()
        )
    }

    // --- Validation ---

    private fun isValidPostcode(postcode: String): Boolean {
        return postcode.matches(POSTCODE_REGEX)
    }

    companion object {
        private val POSTCODE_REGEX = "^[A-Z]{1,2}[0-9][0-9A-Z]?[0-9][A-Z]{2}$".toRegex()
    }
}
