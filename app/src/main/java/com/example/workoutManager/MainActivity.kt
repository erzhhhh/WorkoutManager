package com.example.workoutManager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.example.workoutManager.databinding.ActivityMainBinding
import com.example.workoutManager.exerciseDetails.ExerciseDetailsFragment
import com.example.workoutManager.exerciseList.ExerciseListFragment
import com.example.workoutManager.models.Exercise
import com.example.workoutManager.utils.OnItemClickListener

class MainActivity : AppCompatActivity(), OnItemClickListener<Exercise> {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                this.replace(R.id.fragmentContainer, ExerciseListFragment())
            }
        }
    }

    override fun onItemClick(item: Exercise) {
        if (binding.fragmentDetailsContainer != null) {
            val fragment = ExerciseDetailsFragment.newInstance(item)
            supportFragmentManager.commit {
                this.replace(R.id.fragmentDetailsContainer, fragment)
            }
        } else {
            val fragment = ExerciseDetailsFragment.newInstance(item)
            supportFragmentManager.commit {
                this.replace(R.id.fragmentContainer, fragment)
                this.addToBackStack(null)
            }
        }
    }
}