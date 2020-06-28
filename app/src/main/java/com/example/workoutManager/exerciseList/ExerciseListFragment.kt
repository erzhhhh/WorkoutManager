package com.example.workoutManager.exerciseList

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.workoutManager.App
import com.example.workoutManager.MainActivity
import com.example.workoutManager.R
import com.example.workoutManager.api.WorkManagerService
import com.example.workoutManager.databinding.FragmentListBinding
import com.example.workoutManager.models.Exercise
import com.example.workoutManager.repo.CategoryRepo
import com.example.workoutManager.utils.OnItemClickListener
import com.example.workoutManager.utils.OnRetryClickListener
import javax.inject.Inject


class ExerciseListFragment : Fragment() {

    @Inject
    lateinit var dataSource: WorkManagerService
    @Inject
    lateinit var categoryRepo: CategoryRepo

    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    private val viewModel by viewModels<ExerciseListViewModel> {
        ExerciseListViewModelFactory(
            dataSource,
            categoryRepo
        )
    }

    private lateinit var binding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
                item?.let {
                    (activity as MainActivity).onItemClick(it)
                }
            }
        }

        binding.retryClickListener = object : OnRetryClickListener {
            override fun onButtonClick() {
                viewModel.retry()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.actionSearch)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchItem?.let {
            searchView = it.actionView as SearchView
        }

        searchView?.let {
            it.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.filterText.postValue(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
            }
            it.setOnQueryTextListener(queryTextListener)

            it.setOnCloseListener {
                viewModel.filterText.postValue("")
                true
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSearch ->
                return false
            else -> {
            }
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

}
