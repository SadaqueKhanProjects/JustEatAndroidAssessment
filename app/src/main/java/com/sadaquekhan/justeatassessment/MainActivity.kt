package com.sadaquekhan.justeatassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sadaquekhan.justeatassessment.ui.screen.RestaurantScreen
import com.sadaquekhan.justeatassessment.ui.theme.JustEatAndroidAssessmentTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main entry point of the app.
 *
 * Annotated with [@AndroidEntryPoint] to allow Hilt injection into this activity.
 * Sets up the UI using Jetpack Compose and renders [RestaurantScreen].
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Initializes the UI content using Jetpack Compose and sets the root Composable.
     *
     * This ensures:
     * - Hilt dependencies are injected before content is rendered
     * - Theme is applied across the app
     * - [RestaurantScreen] is shown as the default screen
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustEatAndroidAssessmentTheme {
                RestaurantScreen()
            }
        }
    }
}
