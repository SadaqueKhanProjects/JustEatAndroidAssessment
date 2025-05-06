package com.sadaquekhan.justeatassessment.di

import com.sadaquekhan.justeatassessment.data.remote.dto.mapper.RestaurantMapper
import com.sadaquekhan.justeatassessment.data.repository.IRestaurantRepository
import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepositoryImpl
import com.sadaquekhan.justeatassessment.domain.mapper.IRestaurantMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module for binding interfaces to implementations.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindRestaurantRepository(
        impl: RestaurantRepositoryImpl
    ): IRestaurantRepository

    @Binds
    abstract fun bindRestaurantMapper(
        impl: RestaurantMapper
    ): IRestaurantMapper
}
