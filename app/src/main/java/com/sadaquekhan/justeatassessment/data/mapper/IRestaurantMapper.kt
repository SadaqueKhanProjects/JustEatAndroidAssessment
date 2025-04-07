package com.sadaquekhan.justeatassessment.domain.mapper

import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**
 * Maps a [RestaurantDto] from the data layer to a [Restaurant] domain model.
 *
 * Allows injection and mocking via interface-based architecture, improving testability
 * and aligning with clean architecture principles.
 */
interface IRestaurantMapper {

    /**
     * Converts a raw [RestaurantDto] into a sanitized, UI-ready [Restaurant] object.
     *
     * @param dto The raw data model received from the API
     * @return A clean domain model used for display or business logic
     */
    fun mapToDomainModel(dto: RestaurantDto): Restaurant
}
