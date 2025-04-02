package com.sadaquekhan.justeatassessment.network.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object ApiClient {

    // Base URL for the Just Eat UK API
    private const val BASE_URL = "https://uk.api.just-eat.io/"

    // Logging Interceptor to debug network requests
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient with the logging interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit instance with Moshi converter
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}