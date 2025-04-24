package com.example.newproject

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Application:Application() {
    override fun onCreate() {
        super.onCreate()

    }
    var activity: Activity? = null
}