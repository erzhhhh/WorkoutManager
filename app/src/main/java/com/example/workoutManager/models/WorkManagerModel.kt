package com.example.workoutManager.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WorkManagerResponse(
    @Expose
    @SerializedName("count")
    val count: String,
    @Expose
    @SerializedName("next")
    val nextPageUrl: String,
    @Expose
    @SerializedName("previous")
    val previousPageUrl: String,
    @Expose
    @SerializedName("results")
    val exerciseList: List<Exercise>
)

data class Exercise(
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("category")
    val category: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("muscles")
    val muscleList: List<String>,
    @Expose
    @SerializedName("equipment")
    val equipmentList: List<String>,
    val imageUrl: String?
)

data class Image(
    @Expose
    @SerializedName("thumbnail_cropped")
    val thumbnailCropped: ImageInfo
)

data class ImageInfo(
    @Expose
    @SerializedName("url")
    val url: String
)
