package com.example.workoutManager.models

import com.google.gson.annotations.SerializedName

data class WorkManagerResponse(
    @SerializedName("count")
    val count: String,
    @SerializedName("next")
    val nextPageUrl: String,
    @SerializedName("previous")
    val previousPageUrl: String,
    @SerializedName("results")
    val exerciseList: List<Exercise>
)

data class Exercise(
    @SerializedName("category")
    val category: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("muscles")
    val muscleList: List<String>
)
