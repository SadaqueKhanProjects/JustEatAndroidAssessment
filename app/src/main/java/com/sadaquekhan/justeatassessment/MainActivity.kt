package com.sadaquekhan.justeatassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sadaquekhan.justeatassessment.ui.screen.RestaurantScreenPreviewWrapper // ⬅️ Import your preview wrapper
import com.sadaquekhan.justeatassessment.ui.theme.JustEatAndroidAssessmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustEatAndroidAssessmentTheme {
                // ✅ TEMPORARY: Replace this line:
                // RestaurantScreen()

                // ✅ WITH THIS:
                RestaurantScreenPreviewWrapper()
            }
        }
    }
}