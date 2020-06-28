package com.example.workoutManager.adapters

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workoutManager.R
import com.example.workoutManager.exerciseList.ExerciseListViewModel
import com.example.workoutManager.models.Category
import com.example.workoutManager.models.Exercise
import com.example.workoutManager.models.NetworkState
import com.example.workoutManager.utils.OnItemClickListener
import com.example.workoutManager.utils.OnRetryClickListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


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

//@BindingAdapter(
//    value = ["singleCheckedChip"],
//    requireAll = true
//)
//fun bindSingleCheckedChip(group: ChipGroup, tag: String?) {
//    tag?.let {
//        group.findViewWithTag<Chip>(tag)?.let { chip ->
//            if (!chip.isChecked) chip.isChecked = true
//        } ?: group.check(View.NO_ID)
//    } ?: run {
//        group.check(View.NO_ID)
//    }
//}
//
//@InverseBindingAdapter(attribute = "singleCheckedChip")
//fun getSingleCheckedChip(group: ChipGroup): String? {
//    return if (group.checkedChipId != View.NO_ID) {
//        group.findViewById<Chip>(group.checkedChipId)?.tag as? String
//    } else {
//        null
//    }
//}
//
//@BindingAdapter("singleCheckedChipAttrChanged")
//fun setSingleCheckedChipListener(
//    group: ChipGroup,
//    attrChange: InverseBindingListener
//) {
//    group.setOnCheckedChangeListener { _, _ -> attrChange.onChange() }
//}

@BindingAdapter(
    value = ["categories", "onChipClickListener"],
    requireAll = true
)
fun bindCategoriesChip(
    group: ChipGroup,
    categories: List<Category>?,
    viewModel: ExerciseListViewModel
) {

    if (group.size != categories?.size ?: 0) {
        categories?.let {
            for (index in categories.indices) {
                val chip = Chip(group.context)
                with(chip) {
                    text = categories[index].name
                    tag = categories[index].name
                    isCheckable = true
                }
                group.addView(chip)
                group.invalidate()
            }
        }

        group.setOnCheckedChangeListener { _, checkedId ->
            group.findViewById<Chip>(checkedId)?.let { chip ->
                if (!chip.isChecked) {
                    chip.isChecked = true
                }
                viewModel.chipClickListener(chip.text.toString())
            } ?: group.check(View.NO_ID)
        }
    }
}
