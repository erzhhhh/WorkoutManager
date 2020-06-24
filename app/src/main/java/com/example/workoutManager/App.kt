package com.example.workoutManager

import androidx.multidex.MultiDexApplication
import com.example.workoutManager.di.AppModule

class App : MultiDexApplication() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(
                AppModule(
                    this,
                    "https://wger.de/api/"
                )
            )
            .build()
        super.onCreate()
    }
}