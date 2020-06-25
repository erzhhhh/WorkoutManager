package com.example.workoutManager.di

import android.content.Context
import com.example.workoutManager.api.AuthInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(
    includes = [
        WorkManagerModule::class
    ]
)
open class AppModule(private val context: Context, private val baseUrl: String) {

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
        )
    }


    @Singleton
    @Provides
    fun provideInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Singleton
    @Provides
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        provideGsonConverterFactory: GsonConverterFactory,
        provideRxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        provideOkHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(provideGsonConverterFactory)
            .addCallAdapterFactory(provideRxJava2CallAdapterFactory)
            .client(provideOkHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }
}