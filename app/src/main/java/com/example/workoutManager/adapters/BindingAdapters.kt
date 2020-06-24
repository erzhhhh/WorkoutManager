package com.example.workoutManager.adapters

import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutManager.models.Exercise


@BindingAdapter(
    value = ["items"]
)
fun setPagerItems(
    recyclerView: RecyclerView,
    offerPagerItems: PagedList<Exercise>?
) {
    recyclerView.run {
        (adapter as? ExerciseRecyclerViewAdapter ?: ExerciseRecyclerViewAdapter(
        )
            .also { adapter = it })
            .also { it.submitList(offerPagerItems) }
    }
}
