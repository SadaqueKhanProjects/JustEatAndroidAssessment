package com.sadaquekhan.justeatassessment.di

import com.sadaquekhan.justeatassessment.data.remote.dto.mapper.RestaurantMapper
import com.sadaquekhan.justeatassessment.data.repository.IRestaurantRepository
import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepositoryImpl
import com.sadaquekhan.justeatassessment.domain.mapper.IRestaurantMapper
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import com.sadaquekhan.justeatassessment.util.AndroidLogger
import com.sadaquekhan.justeatassessment.util.Logger
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Hilt module responsible for binding implementations to interfaces.
 * This ensures that dependencies can be injected where needed
 * using clean architecture principles.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    /**
     * Binds RestaurantRepositoryImpl to RestaurantRepository interface.
     * Allows for flexibility and testability across the app.
     */
    @Binds
    abstract fun bindRestaurantRepository(
        impl: RestaurantRepositoryImpl
    ): IRestaurantRepository



    /**
     * Binds RestaurantMapper to the IRestaurantMapper interface.
     * Enables interface-based injection and mocking during testing.
     */
    @Binds
    abstract fun bindRestaurantMapper(
        impl: RestaurantMapper
    ): IRestaurantMapper
}

/**
 * Hilt module that provides external dependencies like Retrofit and Logger.
 * Singleton scope ensures one instance is shared across the app lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a configured Retrofit instance for network calls.
     * Uses Moshi for JSON serialization and deserialization.
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://uk.api.just-eat.io/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    /**
     * Provides an implementation of RestaurantApiService using Retrofit.
     * Allows for type-safe API requests based on defined endpoints.
     */
    @Provides
    @Singleton
    fun provideRestaurantApiService(retrofit: Retrofit): RestaurantApiService {
        return retrofit.create(RestaurantApiService::class.java)
    }

    /**
     * Provides an Android-specific logger implementation.
     * Can be swapped out with a fake logger during tests using @TestInstallIn.
     */
    @Provides
    @Singleton
    fun provideLogger(): Logger = AndroidLogger()
}