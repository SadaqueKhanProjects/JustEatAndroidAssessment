package com.sadaquekhan.justeatassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sadaquekhan.justeatassessment.ui.screen.RestaurantScreen
import com.sadaquekhan.justeatassessment.ui.theme.JustEatAndroidAssessmentTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point of the app.
 * Hilt-injected activity that sets the content view using Compose.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustEatAndroidAssessmentTheme {
                // Entry composable for the UI
                RestaurantScreen()
            }
        }
    }
}
