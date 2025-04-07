package com.sadaquekhan.justeatassessment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Base application class for the Just Eat Android Assessment app.
 *
 * This class is annotated with [@HiltAndroidApp], which triggers Hilt’s code generation
 * and sets up the dependency graph at app launch.
 *
 * This is required for Hilt to inject dependencies into Android components like ViewModels, Activities, etc.
 */
@HiltAndroidApp
open class JustEatAssessmentApplication : Application()
