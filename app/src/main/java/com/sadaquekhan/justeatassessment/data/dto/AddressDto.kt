package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * DTO representing a restaurant's address as received from the Just Eat API.
 *
 * This data class contains granular address details and is used as a nested component
 * within the RestaurantDto model.
 *
 * @property firstLine Street or house/building name
 * @property city City where the restaurant is located
 * @property postalCode UK postal code of the restaurant
 *
 * @see RestaurantDto – uses this class as part of its full data representation.
 */
@JsonClass(generateAdapter = true)
data class AddressDto(
    val firstLine: String,
    val city: String,
    val postalCode: String
)
