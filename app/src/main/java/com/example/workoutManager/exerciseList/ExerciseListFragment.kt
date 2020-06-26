package com.example.workoutManager.exerciseList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.workoutManager.App
import com.example.workoutManager.api.WorkManagerService
import com.example.workoutManager.databinding.FragmentListBinding
import com.example.workoutManager.models.Exercise
import com.example.workoutManager.utils.OnItemClickListener
import javax.inject.Inject

class ExerciseListFragment : Fragment() {

    @Inject
    lateinit var dataSource: WorkManagerService

    private val viewModel by viewModels<ExerciseListViewModel> {
        ExerciseListViewModelFactory(
            dataSource
        )
    }

    private lateinit var binding: FragmentListBinding

    override fun onAttach(context: Context) {
        (context.applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.itemClickListener = object : OnItemClickListener<Exercise?> {
            override fun onItemClick(item: Exercise?) {

            }
        }
    }
}
