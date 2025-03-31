package com.sadaquekhan.justeatassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sadaquekhan.justeatassessment.ui.screen.RestaurantScreen
import com.sadaquekhan.justeatassessment.ui.theme.JustEatAndroidAssessmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustEatAndroidAssessmentTheme {
                // Main screen handles its own state and logic
                RestaurantScreen()
            }
        }
    }
}