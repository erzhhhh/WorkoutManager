package com.example.workoutManager.api

import com.example.workoutManager.models.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface WorkManagerService {

    @GET("v2/exercise/?status=2")
    fun getPage(@Query("category") value: String?): Single<WorkManagerResponse>

    @GET("v2/exerciseimage/{id}/thumbnails/")
    fun getImageUrl(@Path("id") id: String): Single<Image>

    @GET
    fun getNextPage(@Url url: String): Single<WorkManagerResponse>

    @GET("v2/exercisecategory/")
    fun getCategories(): Single<CategoryResponse>

    @GET("v2/equipment/")
    fun getEquipment(): Single<EquipmentResponse>

    @GET("v2/muscle/")
    fun getMuscles(): Single<MuscleResponse>
}