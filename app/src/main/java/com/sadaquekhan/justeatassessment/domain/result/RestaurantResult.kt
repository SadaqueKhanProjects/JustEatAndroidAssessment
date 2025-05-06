// File: RestaurantResult.kt
package com.sadaquekhan.justeatassessment.domain.result

import com.sadaquekhan.justeatassessment.domain.model.Restaurant

sealed class RestaurantResult {
    data class Success(val restaurants: List<Restaurant>) : RestaurantResult()
    data class NetworkError(val message: String) : RestaurantResult()
    data class Timeout(val message: String) : RestaurantResult()
    data class UnknownError(val message: String) : RestaurantResult()
}
