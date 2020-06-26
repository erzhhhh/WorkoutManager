package com.example.workoutManager.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workoutManager.models.Exercise
import com.example.workoutManager.models.NetworkState
import com.example.workoutManager.utils.OnItemClickListener
import com.example.workoutManager.utils.OnRetryClickListener


@BindingAdapter(
    value = ["items", "onItemClickListener", "retryClickListener", "networkState"],
    requireAll = true
)
fun setPagerItems(
    recyclerView: RecyclerView,
    offerPagerItems: PagedList<Exercise>?,
    onItemClickListener: OnItemClickListener<Exercise>,
    onRetryClickListener: OnRetryClickListener,
    networkState: NetworkState?
) {
    recyclerView.run {
        (adapter as? ExerciseRecyclerViewAdapter ?: ExerciseRecyclerViewAdapter(
            onItemClickListener,
            onRetryClickListener
        )
            .also { it.onItemClickListener = onItemClickListener }
            .also { it.onRetryClickListener = onRetryClickListener }
            .also { adapter = it })
            .also { it.submitList(offerPagerItems) }
            .also { it.setNetworkState(networkState) }
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
