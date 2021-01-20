package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.EspressoIdlingResource
import com.example.test.R
import com.example.test.adapter.PeopleAdapter
import com.example.test.databinding.FragmentPeopleBinding
import com.example.test.model.Person
import com.example.test.mvi.action.PeopleViewAction
import com.example.test.mvi.state.PeopleViewState
import com.example.test.viewmodel.PeopleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PeopleFragment : Fragment() {

    private val peopleViewModel: PeopleViewModel by viewModels()
    private lateinit var binding: FragmentPeopleBinding
    private lateinit var peopleAdapter: PeopleAdapter

    private var twoPane: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPeopleBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
        loadPeople()

        binding.detailsNavContainer?.let {
            twoPane = true
        }
    }

    private fun loadPeople() {
        EspressoIdlingResource.increment()
        lifecycleScope.launchWhenResumed {
            peopleViewModel.userIntent.send(PeopleViewAction.LoadPeople)
        }
    }

    private fun setupUI() {
        peopleAdapter = PeopleAdapter { action: PeopleViewAction.LoadPersonDetails ->
            lifecycleScope.launch {
                peopleViewModel.userIntent.send(action)
            }
        }
        binding.peopleRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = peopleAdapter
        }
    }

    private fun displayDetails(person: Person) {
        if (twoPane) {
            displaySideDetails(person)
        } else {
            displayOtherViewDetails(person)
        }
    }

    private fun displaySideDetails(person: Person) {
        val fragment = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(DetailsFragment.PERSON_DATA, person)
            }
        }
        requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.detailsNavContainer, fragment)
                .commit()
    }

    private fun displayOtherViewDetails(person: Person) {
        val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
            putExtra(DetailsFragment.PERSON_DATA, person)
        }
        requireContext().startActivity(intent)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            peopleViewModel.state.collect {
                when (it) {
                    is PeopleViewState.Idle -> {

                    }
                    is PeopleViewState.Loading -> {
                        binding.peopleProgressBar.visibility = View.VISIBLE
                    }

                    is PeopleViewState.LoadedPersons -> {
                        binding.peopleProgressBar.visibility = View.GONE
                        renderList(it.people)
                        Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                    }
                    is PeopleViewState.Error -> {
                        binding.peopleProgressBar.visibility = View.GONE
                        Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                    }
                    is PeopleViewState.GoToDetails -> {
                        displayDetails(it.person)
                    }
                }
            }
        }
        EspressoIdlingResource.decrement()
    }

    private fun renderList(persons: List<Person>) {
        binding.peopleRecyclerView.visibility = View.VISIBLE
        peopleAdapter.addData(persons)

        EspressoIdlingResource.decrement()
    }
}