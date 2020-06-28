package com.example.workoutManager.exerciseList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.workoutManager.ExercisesDataFactory
import com.example.workoutManager.api.WorkManagerService
import com.example.workoutManager.models.CategoriesConstants.ABS
import com.example.workoutManager.models.CategoriesConstants.ARMS
import com.example.workoutManager.models.CategoriesConstants.BACK
import com.example.workoutManager.models.CategoriesConstants.CALVES
import com.example.workoutManager.models.CategoriesConstants.CHEST
import com.example.workoutManager.models.CategoriesConstants.LEGS
import com.example.workoutManager.models.CategoriesConstants.SHOULDERS
import com.example.workoutManager.models.Category
import com.example.workoutManager.models.Exercise
import com.example.workoutManager.models.NetworkState
import com.example.workoutManager.repo.CategoryRepo
import java.util.*

open class ExerciseListViewModel(
    private val service: WorkManagerService,
    private val categoryRepo: CategoryRepo
) : ViewModel() {

    private lateinit var networkState: LiveData<NetworkState>
    lateinit var isLoading: LiveData<Boolean>
    lateinit var categoriesChips: LiveData<List<Category>>

    private var _childModels = MutableLiveData<PagedList<Exercise>>()
    var childModels: LiveData<PagedList<Exercise>> = _childModels
    val filterText = MutableLiveData<String>()

    private var searchCategory: String? = null
    private lateinit var factory: ExercisesDataFactory

    fun chipClickListener(name: String) {
        searchCategory = when (name.toLowerCase(Locale.ROOT)) {
            ARMS -> "8"
            LEGS -> "9"
            ABS -> "10"
            CHEST -> "11"
            BACK -> "12"
            SHOULDERS -> "13"
            CALVES -> "14"
            else -> null
        }

        filterText.value = filterText.value
    }

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

        factory = ExercisesDataFactory(service, categoryRepo)

        childModels = Transformations.switchMap(filterText) { input ->
            loadExerciseList(input, pagedListConfig)
        }

        networkState = Transformations.switchMap(factory.mutableDataSource) {
            it.initialLoad
        }

        isLoading = Transformations.switchMap(factory.mutableDataSource) {
            it.isLoading
        }

        categoriesChips = Transformations.switchMap(factory.mutableDataSource) {
            it.categoriesChips
        }
    }

    private fun loadExerciseList(
        searchKey: String,
        pagedListConfig: PagedList.Config
    ): LiveData<PagedList<Exercise>> {

        return if (searchKey.isEmpty()) {
            factory.searchKey = ""
            factory.filterQuery = searchCategory
            LivePagedListBuilder(factory, pagedListConfig).build()
        } else {
            factory.searchKey = searchKey
            factory.filterQuery = searchCategory
            LivePagedListBuilder(factory, pagedListConfig).build()
        }
    }

    fun retry() {
        factory.retry.invoke()
    }
}