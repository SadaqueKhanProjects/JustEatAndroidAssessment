package com.sadaquekhan.justeatassessment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for Just Eat Assessment.
 *
 * Annotated with `@HiltAndroidApp` to initialize Hilt's dependency graph
 * and enable field injection across the entire app.
 */
@HiltAndroidApp
class JustEatAssessmentApplication : Application()
