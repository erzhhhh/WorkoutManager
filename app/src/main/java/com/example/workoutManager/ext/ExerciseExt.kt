package com.example.workoutManager.ext

import com.example.workoutManager.models.Exercise

fun List<Exercise>.mapDetails(
    equipment: Map<String, String>,
    muscles: Map<String, String>
): List<Exercise> {
    return this.map {
        it.mapDetail(equipment, muscles)
    }
}

fun Exercise.mapDetail(
    equipment: Map<String, String>,
    muscles: Map<String, String>
): Exercise {

    val newEquipList = mutableListOf<String>()
    this.equipmentList.forEach {
        equipment[it]?.let {
            newEquipList.add(it)
        }
    }

    val newMuscleList = mutableListOf<String>()
    this.muscleList.forEach {
        muscles[it]?.let {
            newMuscleList.add(it)
        }
    }

    return when (this.category) {
        "8" -> this.copy(
            category = "Arms",
            equipmentList = newEquipList,
            muscleList = newMuscleList
        )
        "9" -> this.copy(
            category = "Legs",
            equipmentList = newEquipList,
            muscleList = newMuscleList
        )
        "10" -> this.copy(
            category = "Abs",
            equipmentList = newEquipList,
            muscleList = newMuscleList
        )
        "11" -> this.copy(
            category = "Chest",
            equipmentList = newEquipList,
            muscleList = newMuscleList
        )
        "12" -> this.copy(
            category = "Back",
            equipmentList = newEquipList,
            muscleList = newMuscleList
        )
        "13" -> this.copy(
            category = "Shoulders",
            equipmentList = newEquipList,
            muscleList = newMuscleList
        )
        "14" -> this.copy(
            category = "Calves",
            equipmentList = newEquipList,
            muscleList = newMuscleList
        )
        else -> this
    }
}