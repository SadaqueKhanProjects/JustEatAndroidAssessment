package com.sadaquekhan.justeatassessment.data.remote.dto.mapper

import com.sadaquekhan.justeatassessment.data.remote.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import javax.inject.Inject

/**
 * Maps RestaurantDto to Restaurant domain model.
 */
class RestaurantMapper @Inject constructor() {
    fun toDomain(dto: RestaurantDto): Restaurant {
        return Restaurant(
            id = dto.id,
            name = dto.name,
            cuisines = dto.cuisines.map { it.name },
            rating = dto.rating.starRating,
            addressLine = dto.address.firstLine,
            city = dto.address.city,
            postalCode = dto.address.postalCode
        )
    }
}