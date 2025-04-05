package com.sadaquekhan.justeatassessment.domain.mapper

import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**
 * Interface for mapping a RestaurantDto into a domain Restaurant model.
 * This allows for flexible injection and testing.
 */
interface IRestaurantMapper {
    fun mapToDomainModel(dto: RestaurantDto): Restaurant
}