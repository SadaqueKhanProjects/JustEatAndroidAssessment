package com.sadaquekhan.justeatassessment.di

import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepository
import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepositoryImpl
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

// Hilt module to provide app-level dependencies.
@Module
@InstallIn(SingletonComponent::class) // Scope: entire app lifecycle
object AppModule {

    // Provides a singleton instance of the Retrofit API service.
    @Provides
    @Singleton
    fun provideRestaurantApiService(): RestaurantApiService {
        return Retrofit.Builder()
            .baseUrl("https://uk.api.just-eat.io/") // Just Eat API base URL
            .addConverterFactory(MoshiConverterFactory.create()) // Converts JSON to Kotlin objects
            .build()
            .create(RestaurantApiService::class.java)
    }

    // Provides a singleton RestaurantRepository, implementing its interface.
    @Provides
    @Singleton
    fun provideRestaurantRepository(apiService: RestaurantApiService): RestaurantRepository {
        return RestaurantRepositoryImpl(apiService)
    }
}