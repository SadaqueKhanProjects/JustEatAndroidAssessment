package com.sadaquekhan.justeatassessment.util

import android.util.Log
import javax.inject.Inject

/**
 * Android-specific implementation of Logger.
 * Wraps android.util.Log for logging in production builds.
 */
class AndroidLogger @Inject constructor() : Logger {

    /**
     * Logs a debug message with the provided tag.
     */
    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }

    /**
     * Logs an error message with the provided tag.
     */
    override fun error(tag: String, message: String) {
        Log.e(tag, message)
    }
}