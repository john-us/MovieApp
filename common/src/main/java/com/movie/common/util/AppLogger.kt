package com.movie.common.util

import android.util.Log
import com.movie.common.BuildConfig


fun Any.tag(): String {
    return this::class.java.simpleName;
}

class AppLogger {
    companion object {
        fun writeLog(logLevel: LogLevel, tag: String, msg: String) {
            if (BuildConfig.DEBUG) {
                when (logLevel) {
                    LogLevel.DEBUG -> Log.println(Log.DEBUG, tag, msg)
                    LogLevel.VERBOSE -> Log.println(Log.VERBOSE, tag, msg)
                    LogLevel.INFO -> Log.println(Log.INFO, tag, msg)
                    LogLevel.WARNING -> Log.println(Log.WARN, tag, msg)
                    LogLevel.ERROR -> Log.println(Log.ERROR, tag, msg)
                    LogLevel.ASSERT -> Log.println(Log.ASSERT, tag, msg)
                }
            }
        }
    }

    enum class LogLevel {
        VERBOSE,
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        ASSERT;
    }
}