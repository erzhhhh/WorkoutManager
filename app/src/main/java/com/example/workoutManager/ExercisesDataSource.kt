package com.example.workoutManager

import android.annotation.SuppressLint
import androidx.paging.PageKeyedDataSource
import com.example.workoutManager.api.WorkManagerService
import com.example.workoutManager.models.Exercise
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class ExercisesDataSource(
    private val service: WorkManagerService
) : PageKeyedDataSource<Int, Exercise>() {

    private var nextPageUrl: String? = null
    private var previousPageUrl: String? = null

    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Exercise>
    ) {
        val currentPage = 1
        val nextPage = currentPage + 1

        val list = mutableListOf<Exercise>()
        service.getExerciseList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    list.addAll(it.exerciseList)
                    nextPageUrl = it.nextPageUrl

                    callback.onResult(list, 0, nextPage)
                },
                {
                    loadInitial(params, callback)
                }
            )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Exercise>) {

        val currentPage = params.key
        val nextPage = currentPage + 1

        nextPageUrl?.let { url ->
            val list = mutableListOf<Exercise>()
            service.getNextPage(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        list.addAll(it.exerciseList)
                        previousPageUrl = it.previousPageUrl
                        nextPageUrl = it.nextPageUrl

                        callback.onResult(list, nextPage)
                    },
                    {
                        loadAfter(params, callback)
                    }
                )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Exercise>) {
    }
}