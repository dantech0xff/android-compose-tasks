package com.creative.androidtasks

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by dan on 15/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("App", "onCreate")
    }
}