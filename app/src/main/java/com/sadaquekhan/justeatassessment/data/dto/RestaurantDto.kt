package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * Raw API restaurant representation before sanitization.
 *
 * @property rating Nullable because:
 *                 - API may omit rating for new restaurants
 *                 - Nested rating object may be missing
 */
@JsonClass(generateAdapter = true)
data class RestaurantDto(
    val id: String,
    val name: String,
    val cuisines: List<CuisineDto>,
    val rating: RatingDto?,
    val address: AddressDto
)