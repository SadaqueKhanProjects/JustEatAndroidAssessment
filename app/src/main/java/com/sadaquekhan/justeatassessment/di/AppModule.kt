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

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindRestaurantRepository(
        impl: RestaurantRepositoryImpl
    ): RestaurantRepository
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://uk.api.just-eat.io/") // ✅ Correct base URL
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRestaurantApiService(retrofit: Retrofit): RestaurantApiService {
        return retrofit.create(RestaurantApiService::class.java)
    }
}