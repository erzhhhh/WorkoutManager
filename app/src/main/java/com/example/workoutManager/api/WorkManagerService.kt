package com.example.workoutManager.api

import com.example.workoutManager.models.Image
import com.example.workoutManager.models.WorkManagerResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface WorkManagerService {

    @GET("v2/exercise")
    fun getPage(): Single<WorkManagerResponse>

    @GET("v2/exerciseimage/{id}/thumbnails/")
    fun getImageUrl(@Path("id") id: String): Single<Image>

    @GET
    fun getNextPage(@Url url: String): Single<WorkManagerResponse>
}