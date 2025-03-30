// Domain model representing a single restaurant
package com.sadaquekhan.justeatassessment.domain.model

data class Restaurant(
    val id: String,
    val name: String,
    val rating: Double,
    val cuisineType: String,
    val address: String
)