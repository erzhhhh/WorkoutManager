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
    private val service: WorkManagerService
) : PageKeyedDataSource<String, Exercise>() {

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Exercise>
    ) {
        createRequest(service.getPage()) { data, prevUrl, nextUrl ->
            callback.onResult(data, prevUrl, nextUrl)
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Exercise>) {
        createRequest(service.getNextPage(params.key)) { data, _, nextUrl ->
            callback.onResult(data, nextUrl)
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Exercise>) {
        createRequest(service.getNextPage(params.key)) { data, prevUrl, _ ->
            callback.onResult(data, prevUrl)
        }
    }

    @SuppressLint("CheckResult")
    private fun createRequest(
        method: Single<WorkManagerResponse>,
        callBack: (data: List<Exercise>, prevUrl: String?, nextUrl: String?) -> Unit
    ) {
        method.toObservable()
            .flatMapIterable(
                { response -> response.exerciseList },
                { response: WorkManagerResponse, exercise: Exercise ->
                    NextItemGroup(
                        nextUrl = response.nextPageUrl,
                        data = exercise
                    )
                }
            )
            .flatMap(
                { (_, exercise: Exercise) ->
                    service.getImageUrl(exercise.id)
                        .map { Optional(it) }
                        .onErrorReturn { Optional(null) }
                        .toObservable()
                },
                { item: NextItemGroup<Exercise>, imageOptional: Optional<Image> ->
                    item.copy(
                        data = item.data.copy(
                            imageUrl = imageOptional.data?.thumbnailCropped?.url,
                            bigImageUrl = imageOptional.data?.medium?.url
                        )
                    )
                }
            )
            .subscribeOn(Schedulers.io())
            .toList()
            .map {
                NextItemGroup(
                    it.first().nextUrl,
                    it.map { it.data }
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { (nextUrl, list) ->
                    callBack(list, null, nextUrl)
                },
                {
                    it.printStackTrace()
                }
            )
    }

    data class NextItemGroup<T : Any>(
        val nextUrl: String,
        val data: T
    )

    data class Optional<T>(val data: T?)
}