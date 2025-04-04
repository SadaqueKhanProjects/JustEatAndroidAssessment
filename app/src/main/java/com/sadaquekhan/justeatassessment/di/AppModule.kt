package com.sadaquekhan.justeatassessment.di

import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepository
import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepositoryImpl
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Provides dependency injection bindings for repository interfaces.
 *
 * Uses Hilt's `@Binds` to map the implementation to the interface.
 * Keeps architecture loosely coupled and testable.
 *
 * @see NetworkModule for Retrofit and API service providers.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindRestaurantRepository(
        impl: RestaurantRepositoryImpl
    ): RestaurantRepository
}

/**
 * Provides network-level dependencies using Hilt.
 *
 * Includes Retrofit setup with Moshi for JSON parsing,
 * and initializes the RestaurantApiService for API calls.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://uk.api.just-eat.io/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRestaurantApiService(retrofit: Retrofit): RestaurantApiService {
        return retrofit.create(RestaurantApiService::class.java)
    }
}
