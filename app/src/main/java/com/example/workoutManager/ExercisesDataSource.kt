package com.example.workoutManager

import android.annotation.SuppressLint
import androidx.paging.PageKeyedDataSource
import com.example.workoutManager.api.WorkManagerService
import com.example.workoutManager.models.Exercise
import com.example.workoutManager.models.Image
import com.example.workoutManager.models.WorkManagerResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class ExercisesDataSource(
    private val service: WorkManagerService,
    private val search: String
) : PageKeyedDataSource<String, Exercise>() {

    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Exercise>
    ) {
        createRequest(service.getPage(), search)
            .subscribe(
                {
                    callback.onResult(it.items, null, it.nextPage)
                },
                {
                    it.printStackTrace()
                }
            )
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Exercise>) {
        createRequest(service.getNextPage(params.key), search)
            .subscribe(
                {
                    callback.onResult(it.items, it.nextPage)
                },
                {
                    it.printStackTrace()
                }
            )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Exercise>) {
    }

    private fun createRequest(
        method: Single<WorkManagerResponse>,
        search: String
    ): Single<Page> {
        var nextUrl: String? = null
        return method.toObservable()
            .doOnNext { nextUrl = it.nextPageUrl }
            .flatMapIterable { response -> response.exerciseList }
            .filter { it.name.contains(search, ignoreCase = true) }
            .flatMap(
                { exercise: Exercise ->
                    service.getImageUrl(exercise.id)
                        .map { Optional(it) }
                        .onErrorReturn { Optional(null) }
                        .toObservable()
                },
                { item: Exercise, imageOptional: Optional<Image> ->
                    item.copy(
                        imageUrl = imageOptional.data?.thumbnailCropped?.url,
                        bigImageUrl = imageOptional.data?.medium?.url
                    )
                }
            )
            .subscribeOn(Schedulers.io())
            .toList()
            .map {
                Page(
                    nextUrl,
                    it
                )
            }
            .filter { it.items.isNotEmpty() }
            .switchIfEmpty(
                Single.defer {
                    nextUrl?.let { createRequest(service.getNextPage(it), search) } ?: Single.just(
                        Page(
                            null,
                            listOf()
                        )
                    )
                }
            )
            .observeOn(AndroidSchedulers.mainThread())
    }

    data class Page(
        val nextPage: String?,
        val items: List<Exercise>
    )

    data class Optional<T>(val data: T?)
}