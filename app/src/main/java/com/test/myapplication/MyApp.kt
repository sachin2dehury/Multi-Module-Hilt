package com.test.myapplication

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}
