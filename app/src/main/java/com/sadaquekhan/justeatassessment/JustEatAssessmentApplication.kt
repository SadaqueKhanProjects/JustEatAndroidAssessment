package com.sadaquekhan.justeatassessment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Base application class for the Just Eat Android Assessment.
 * Annotated with @HiltAndroidApp to enable Hilt's dependency injection.
 * Initializes the dependency graph automatically at app startup.
 */
@HiltAndroidApp
class JustEatAssessmentApplication : Application()
