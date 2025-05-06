package com.sadaquekhan.justeatassessment.di

import com.sadaquekhan.justeatassessment.util.logging.AndroidLogger
import com.sadaquekhan.justeatassessment.util.logging.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides core utilities like logging implementations.
 */
@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger = AndroidLogger()
}
