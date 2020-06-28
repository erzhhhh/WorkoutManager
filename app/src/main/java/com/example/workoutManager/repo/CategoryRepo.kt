package com.example.workoutManager.repo

import com.example.workoutManager.api.WorkManagerService
import com.example.workoutManager.models.CategoryResponse
import com.example.workoutManager.models.EquipmentResponse
import com.example.workoutManager.models.MuscleResponse
import io.reactivex.Observable
import io.reactivex.Single

class CategoryRepo(private val service: WorkManagerService) {

    val categories: Single<CategoryResponse> = service.getCategories()
        .flatMap {
            Single.just(it).cache()
        }

    val equipments: Single<EquipmentResponse> = service.getEquipment()
        .flatMap {
            Single.just(it).cache()
        }

    val muscles: Single<MuscleResponse> = service.getMuscles()
        .flatMap {
            Single.just(it).cache()
        }
}