package com.sadaquekhan.justeatassessment.util

/**
 * Logger abstraction for decoupling logging from Android APIs,
 * making it testable in JVM environments.
 */
interface Logger {
    fun debug(tag: String, message: String)
    fun error(tag: String, message: String)
}