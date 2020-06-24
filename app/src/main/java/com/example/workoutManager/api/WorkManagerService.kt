package com.example.workoutManager.api

import com.example.workoutManager.models.WorkManagerResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface WorkManagerService {

    @GET("v2/exercise")
    fun getExerciseList(): Observable<WorkManagerResponse>

    @GET
    fun getNextPage(@Url url: String): Observable<WorkManagerResponse>
}