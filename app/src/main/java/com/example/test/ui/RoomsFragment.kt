package com.example.test.ui

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
import com.example.test.adapter.RoomsAdapter
import com.example.test.databinding.FragmentRoomsBinding
import com.example.test.model.Room
import com.example.test.mvi.action.RoomsViewAction
import com.example.test.mvi.state.RoomsViewState
import com.example.test.viewmodel.RoomsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoomsFragment : Fragment() {

    private val roomsViewModel: RoomsViewModel by viewModels()
    private lateinit var binding: FragmentRoomsBinding
    private lateinit var roomsAdapter: RoomsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRoomsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
        loadRooms()
    }

    private fun loadRooms() {
        EspressoIdlingResource.increment()
        lifecycleScope.launchWhenResumed {
            roomsViewModel.userIntent.send(RoomsViewAction.LoadRooms)
        }
    }

    private fun setupUI() {
        roomsAdapter = RoomsAdapter()
        binding.roomsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = roomsAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            roomsViewModel.state.collect {
                when (it) {
                    is RoomsViewState.Idle -> {

                    }

                    is RoomsViewState.Loading -> {
                        binding.roomsProgressBar.visibility = View.VISIBLE
                    }

                    is RoomsViewState.LoadedRooms -> {
                        binding.roomsProgressBar.visibility = View.GONE
                        renderList(it.rooms)
                        Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                    }

                    is RoomsViewState.Error -> {
                        binding.roomsProgressBar.visibility = View.GONE
                        Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun renderList(rooms: List<Room>) {
        binding.roomsRecyclerView.visibility = View.VISIBLE
        roomsAdapter.addData(rooms)
        EspressoIdlingResource.decrement()
    }
}