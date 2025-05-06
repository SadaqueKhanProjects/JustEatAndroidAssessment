package com.sadaquekhan.justeatassessment.util

/**
 * Abstraction for logging to allow for platform-specific implementations (e.g., Android or test).
 * Enables proper logging without coupling to [android.util.Log].
 */
interface Logger {

    /**
     * Logs a debug-level message.
     *
     * @param tag A tag to identify the logging source
     * @param message The message to log
     */
    fun debug(tag: String, message: String)

    /**
     * Logs an error-level message.
     *
     * @param tag A tag to identify the logging source
     * @param message The message to log
     */
    fun error(tag: String, message: String)
}
