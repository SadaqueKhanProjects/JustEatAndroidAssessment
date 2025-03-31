package com.sadaquekhan.justeatassessment.network.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object ApiClient {

    // Base URL for Just Eat UK API
    private const val BASE_URL = "https://uk.api.just-eat.io/"

    // Logging interceptor to help debug network requests
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Log full request/response
    }

    // Create an OkHttpClient and attach the logging interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Create the Retrofit instance
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Set the API base URL
            .addConverterFactory(MoshiConverterFactory.create()) // Use Moshi for JSON conversion
            .client(client) // Attach custom OkHttp client
            .build()
    }
}