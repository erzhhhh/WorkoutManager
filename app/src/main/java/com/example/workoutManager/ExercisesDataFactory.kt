package com.example.workoutManager

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.workoutManager.api.WorkManagerService
import com.example.workoutManager.models.Exercise

class ExercisesDataFactory(private val service: WorkManagerService, var searchKey: String? = null) :
    DataSource.Factory<String, Exercise>() {

    private lateinit var dataSource: ExercisesDataSource
    val mutableDataSource: MutableLiveData<ExercisesDataSource> = MutableLiveData()
    var retry: () -> Unit = {
        dataSource.retryAllFailed()
    }

    override fun create(): DataSource<String, Exercise> {
        dataSource = ExercisesDataSource(service, searchKey.orEmpty())
        mutableDataSource.postValue(dataSource)
        return dataSource
    }
}