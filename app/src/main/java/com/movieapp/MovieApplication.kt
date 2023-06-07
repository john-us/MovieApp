package com.movieapp

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.movie.common.util.AppLogger
import com.movie.common.util.tag
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApplication : Application(), LifecycleEventObserver {
    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {

            Lifecycle.Event.ON_START -> AppLogger.writeLog(
                AppLogger.LogLevel.DEBUG,
                this.tag(),
                getString(R.string.app_in_fg)
            )

            Lifecycle.Event.ON_STOP -> AppLogger.writeLog(
                AppLogger.LogLevel.DEBUG,
                this.tag(),
                getString(R.string.app_in_bg)
            )

            else -> {
                //do nothing
            }
        }
    }
}