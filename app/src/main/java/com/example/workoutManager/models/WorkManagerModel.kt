package com.example.workoutManager.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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

@Parcelize
data class Exercise(
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("category")
    val category: String,
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("muscles")
    val muscleList: List<String>,
    @Expose
    @SerializedName("equipment")
    val equipmentList: List<String>,
    val imageUrl: String?,
    val bigImageUrl: String?
) : Parcelable

data class Image(
    @Expose
    @SerializedName("thumbnail_cropped")
    val thumbnailCropped: ImageInfo,
    @Expose
    @SerializedName("medium")
    val medium: ImageInfo
)

data class ImageInfo(
    @Expose
    @SerializedName("url")
    val url: String
)
