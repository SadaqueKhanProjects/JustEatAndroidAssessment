package com.sadaquekhan.justeatassessment.data.remote.dto.mapper

import com.sadaquekhan.justeatassessment.data.remote.dto.*
import com.sadaquekhan.justeatassessment.domain.model.*

fun RestaurantDto.toDomain(): Restaurant {
    return Restaurant(
        id = id,
        name = name,
        cuisines = cuisines.map { it.name },
        rating = rating.starRating,
        address = address.toDomain()
    )
}

fun AddressDto.toDomain(): Address {
    return Address(
        firstLine = firstLine ?: "",
        city = city ?: "",
        postalCode = postalCode ?: ""
    )
}