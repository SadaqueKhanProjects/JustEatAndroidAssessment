package com.sadaquekhan.justeatassessment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Marks this application class as the entry point for Hilt.
// Hilt will generate required components and handle dependency graph initialization.
@HiltAndroidApp
class JustEatAssessmentApplication : Application()