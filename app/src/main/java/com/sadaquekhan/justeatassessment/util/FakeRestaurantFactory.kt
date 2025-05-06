package com.sadaquekhan.justeatassessment.util

import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant

object FakeRestaurantFactory {

    fun make(
        id: String = "1",
        name: String = "Test Restaurant",
        cuisines: List<String> = listOf("Italian", "Vegan"),
        rating: Double = 4.5,
        address: Address = Address("123 Main St", "London", "EC1A 1BB")
    ): Restaurant {
        return Restaurant(id = id, name = name, cuisines = cuisines, rating = rating, address = address)
    }

    fun list(count: Int = 3): List<Restaurant> =
        List(count) { index ->
            make(id = (index + 1).toString(), name = "Test Restaurant #$index")
        }
}
