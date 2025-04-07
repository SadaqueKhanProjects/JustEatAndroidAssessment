package com.sadaquekhan.justeatassessment.util

import android.util.Log
import javax.inject.Inject

/**
 * Android-specific logger implementation.
 * Wraps [Log] to allow clean logging throughout the app.
 */
class AndroidLogger @Inject constructor() : Logger {

    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun error(tag: String, message: String) {
        Log.e(tag, message)
    }
}
