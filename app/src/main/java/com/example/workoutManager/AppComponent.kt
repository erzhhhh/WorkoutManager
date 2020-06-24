package com.example.workoutManager

import android.content.Context
import com.example.workoutManager.di.AppModule
import com.example.workoutManager.exerciseList.ExerciseListFragment
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun exposeRetrofit(): Retrofit

    fun exposeContext(): Context

    fun inject(target: ExerciseListFragment)
}