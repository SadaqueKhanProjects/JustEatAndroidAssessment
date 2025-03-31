package com.sadaquekhan.justeatassessment.domain.model

data class Restaurant(
    val name: String,
    val cuisines: List<String>,
    val rating: Double,
    val address: String
)
