package com.example.workoutManager.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workoutManager.models.Exercise
import com.example.workoutManager.utils.OnItemClickListener


@BindingAdapter(
    value = ["items", "itemClickListener"],
    requireAll = true
)
fun setPagerItems(
    recyclerView: RecyclerView,
    offerPagerItems: PagedList<Exercise>?,
    onItemClickListener: OnItemClickListener<Exercise>
) {
    recyclerView.run {
        (adapter as? ExerciseRecyclerViewAdapter ?: ExerciseRecyclerViewAdapter(onItemClickListener)
            .also { it.onItemClickListener = onItemClickListener }
            .also { adapter = it })
            .also { it.submitList(offerPagerItems) }
    }
}


@BindingAdapter(
    value = ["image"]
)
fun setImage(
    imageView: ImageView,
    imageUrl: String?
) {
    imageUrl?.let {
        Glide
            .with(imageView.context)
            .load(it)
            .centerCrop()
            .into(imageView)
            .waitForLayout()
    }
}
