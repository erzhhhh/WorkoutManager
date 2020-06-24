package com.example.workoutManager.di

import com.example.workoutManager.api.WorkManagerService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class WorkManagerModule {

    @Singleton
    @Provides
    fun provideStarWarService(retrofit: Retrofit): WorkManagerService {
        return retrofit.create(WorkManagerService::class.java)
    }
}