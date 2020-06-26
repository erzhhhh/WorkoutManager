package com.example.workoutManager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutManager.databinding.ViewExerciseItemBinding
import com.example.workoutManager.models.Exercise
import com.example.workoutManager.utils.OnItemClickListener

class ExerciseRecyclerViewAdapter(
    var onItemClickListener: OnItemClickListener<Exercise>
) : PagedListAdapter<Exercise, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Exercise>() {
            override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExerciseVH(
            ViewExerciseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExerciseVH) {
            val pm = getItem(position)
            holder.bind(pm)
        }
    }

    inner class ExerciseVH(
        private val binding: ViewExerciseItemBinding,
        onItemClickListener: OnItemClickListener<Exercise>
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemClickListener = onItemClickListener
        }

        fun bind(pm: Exercise?) {
            binding.exercise = pm
        }
    }
}
