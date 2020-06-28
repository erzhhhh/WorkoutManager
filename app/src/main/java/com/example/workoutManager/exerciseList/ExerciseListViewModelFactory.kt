package com.example.workoutManager.exerciseList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workoutManager.api.WorkManagerService
import com.example.workoutManager.repo.CategoryRepo

class ExerciseListViewModelFactory(
    private val dataSource: WorkManagerService,
    private val categoryRepo: CategoryRepo
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseListViewModel::class.java)) {
            return ExerciseListViewModel(dataSource, categoryRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}