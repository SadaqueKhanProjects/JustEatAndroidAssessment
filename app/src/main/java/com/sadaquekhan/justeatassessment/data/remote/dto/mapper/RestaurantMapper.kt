package com.sadaquekhan.justeatassessment.data.remote.dto.mapper

import com.sadaquekhan.justeatassessment.data.remote.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Restaurant

fun RestaurantDto.toDomain(): Restaurant {
    return Restaurant(
        name = this.name,
        cuisines = this.cuisineTypes.map { it.name },
        rating = this.rating,
        address = "${this.address.firstLine}, ${this.address.city}, ${this.address.postcode}"
    )
}