package com.example.workoutManager.adapters

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workoutManager.R
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
    } ?: run {
        imageView.setImageResource(R.drawable.ic_icondefault)
    }
}

@BindingAdapter(
    value = ["formatName", "formatType"],
    requireAll = true
)
fun bindItemText(
    textView: TextView,
    formatName: String?,
    formatType: String
) {
    if (!formatName.isNullOrEmpty()) {
        textView.isVisible = true
        textView.text = formatType.plus(" ").plus(formatName)
    } else {
        textView.isVisible = false
    }
}


@BindingAdapter(
    value = ["formatHtmlName", "formatType"],
    requireAll = true
)
fun bindItemHtmlText(
    textView: TextView,
    formatName: String?,
    formatType: String
) {
    if (!formatName.isNullOrEmpty()) {
        textView.isVisible = true

        textView.text = formatType.plus(" ").plus(Html.fromHtml(formatName))
    } else {
        textView.isVisible = false
    }
}


@BindingAdapter(
    value = ["list", "formatType"],
    requireAll = true
)
fun bindItemList(
    textView: TextView,
    list: List<String>?,
    formatType: String
) {
    if (!list.isNullOrEmpty()) {
        textView.isVisible = true

        val sb = StringBuilder()

        list.forEachIndexed { index, s ->
            if (index == list.lastIndex) {
                sb.append(s)
            } else {
                sb.append(s).append(", ")
            }
        }
        textView.text = formatType.plus(" ").plus(sb.toString())

    } else {
        textView.isVisible = false
    }
}
