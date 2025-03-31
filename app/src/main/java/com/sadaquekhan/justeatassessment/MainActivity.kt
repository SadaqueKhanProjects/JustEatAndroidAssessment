package com.sadaquekhan.justeatassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sadaquekhan.justeatassessment.ui.screen.RestaurantScreen
import com.sadaquekhan.justeatassessment.ui.theme.JustEatAndroidAssessmentTheme
import dagger.hilt.android.AndroidEntryPoint

// Enables Hilt dependency injection in this Activity
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Entry point of the app
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the Compose UI content
        setContent {
            JustEatAndroidAssessmentTheme {
                // Load the main screen
                RestaurantScreen()
            }
        }
    }
}