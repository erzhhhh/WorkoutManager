package com.example.workoutManager.exerciseList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.workoutManager.ExercisesDataFactory
import com.example.workoutManager.api.WorkManagerService
import com.example.workoutManager.models.Exercise
import com.example.workoutManager.models.NetworkState


open class ExerciseListViewModel(private val service: WorkManagerService) : ViewModel() {

    private var _childModels = MutableLiveData<PagedList<Exercise>>()
    var childModels: LiveData<PagedList<Exercise>> = _childModels
    private lateinit var networkState: LiveData<NetworkState>
    val filterText = MutableLiveData<String>()

    private lateinit var factory: ExercisesDataFactory

    init {
        initialize()
    }

    fun currentNetworkState(): LiveData<NetworkState> = networkState

    private fun initialize() {
        filterText.value = ""

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(30)
            .setPageSize(20)
            .build()

        factory = ExercisesDataFactory(service)

        childModels = Transformations.switchMap(filterText) { input ->
            temp(input, pagedListConfig)
        }

        networkState = Transformations.switchMap(factory.mutableDataSource) {
            it.initialLoad
        }
    }

    private fun temp(
        searchKey: String,
        pagedListConfig: PagedList.Config
    ): LiveData<PagedList<Exercise>> {

        return if (searchKey.isEmpty()) {
            LivePagedListBuilder(factory, pagedListConfig).build()
        } else {
            factory.searchKey = searchKey
            LivePagedListBuilder(factory, pagedListConfig).build()
        }
    }

    fun retry() {
        factory.retry.invoke()
    }
}