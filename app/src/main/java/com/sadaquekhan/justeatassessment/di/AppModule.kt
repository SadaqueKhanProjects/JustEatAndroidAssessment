package com.sadaquekhan.justeatassessment.di

import com.sadaquekhan.justeatassessment.data.remote.dto.mapper.RestaurantMapper
import com.sadaquekhan.justeatassessment.data.repository.IRestaurantRepository
import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepositoryImpl
import com.sadaquekhan.justeatassessment.domain.mapper.IRestaurantMapper
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import com.sadaquekhan.justeatassessment.util.logging.AndroidLogger
import com.sadaquekhan.justeatassessment.util.logging.Logger
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Hilt module for binding interface contracts to concrete implementations.
 *
 * Ensures testability and loose coupling by injecting interfaces where possible.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    /**
     * Binds [RestaurantRepositoryImpl] as the concrete implementation of [IRestaurantRepository].
     */
    @Binds
    abstract fun bindRestaurantRepository(
        impl: RestaurantRepositoryImpl
    ): IRestaurantRepository

    /**
     * Binds [RestaurantMapper] to the [IRestaurantMapper] interface.
     * Allows for mocking and test overrides.
     */
    @Binds
    abstract fun bindRestaurantMapper(
        impl: RestaurantMapper
    ): IRestaurantMapper
}

/**
 * Provides third-party or external dependencies used across the app.
 *
 * Includes Retrofit setup and logging configuration.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a configured Retrofit instance.
     *
     * Uses Moshi for JSON parsing and sets Just Eat's base URL.
     *
     * @return A singleton Retrofit client
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
     * Provides an instance of [RestaurantApiService] created via Retrofit.
     */
    @Provides
    @Singleton
    fun provideRestaurantApiService(retrofit: Retrofit): RestaurantApiService {
        return retrofit.create(RestaurantApiService::class.java)
    }

    /**
     * Provides the default Android-based logger implementation.
     * Swap this out in tests using `@TestInstallIn`.
     */
    @Provides
    @Singleton
    fun provideLogger(): Logger = AndroidLogger()
}
