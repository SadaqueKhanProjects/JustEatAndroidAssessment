package com.sadaquekhan.justeatassessment.domain.model


data class Restaurant(
    val id: String,
    val name: String,
    val cuisines: List<String>,
    val rating: Double,
    val addressLine: String,
    val city: String,
    val postalCode: String
)