package com.example.workoutManager.models

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val message: String? = null
) {

    enum class Status {
        RUNNING,
        SUCCESS_LOADED,
        FAILED
    }

    companion object {

        val LOADED = NetworkState(Status.SUCCESS_LOADED)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }
}