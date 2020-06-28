package com.example.workoutManager

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.workoutManager.api.WorkManagerService
import com.example.workoutManager.models.Exercise
import com.example.workoutManager.repo.CategoryRepo

class ExercisesDataFactory(
    private val service: WorkManagerService,
    private val categoryRepo: CategoryRepo,
    var searchKey: String? = null,
    var filterQuery: String? = null
) :
    DataSource.Factory<String, Exercise>() {

    private lateinit var dataSource: ExercisesDataSource
    val mutableDataSource: MutableLiveData<ExercisesDataSource> = MutableLiveData()
    var retry: () -> Unit = {
        dataSource.retryAllFailed()
    }

    override fun create(): DataSource<String, Exercise> {
        dataSource = ExercisesDataSource(service, categoryRepo, searchKey.orEmpty(), filterQuery)
        mutableDataSource.postValue(dataSource)
        return dataSource
    }
}