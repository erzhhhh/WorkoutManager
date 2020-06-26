package com.example.workoutManager.exerciseDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.workoutManager.databinding.FragmentDetailsBinding
import com.example.workoutManager.models.Exercise

private const val EXERCISE = "exercise"

class ExerciseDetailsFragment : Fragment() {

    companion object {

        fun newInstance(item: Exercise) =
            ExerciseDetailsFragment().apply {
                arguments = bundleOf(EXERCISE to item)
            }
    }

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
