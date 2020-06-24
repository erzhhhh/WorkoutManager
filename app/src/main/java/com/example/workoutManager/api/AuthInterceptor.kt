package com.example.workoutManager.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .url(
                    chain.request().url.newBuilder()
                        .addQueryParameter("appid", "49f6af79de71914dc92ba8f659c73bad77435f66")
                        .build()
                )
                .build()
        )
    }
}