package com.example.workoutManager.repo

import com.example.workoutManager.api.WorkManagerService
import com.example.workoutManager.models.CategoryResponse
import io.reactivex.Single

class CategoryRepo(private val service: WorkManagerService) {

    val categories: Single<CategoryResponse> = service.getCategories()
        .flatMap {
            Single.just(it).cache()
        }
}