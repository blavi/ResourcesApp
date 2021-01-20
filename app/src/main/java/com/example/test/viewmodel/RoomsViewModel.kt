package com.example.test.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.interactor.FetchRoomsInteractor
import com.example.test.mvi.action.RoomsViewAction
import com.example.test.mvi.change.RoomsViewChange
import com.example.test.mvi.state.RoomsViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RoomsViewModel @ViewModelInject constructor(private val interactor: FetchRoomsInteractor): ViewModel() {
    val userIntent = Channel<RoomsViewAction>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<RoomsViewState>(RoomsViewState.Idle)

    val state: StateFlow<RoomsViewState>
        get() = _state

    init {
        consumeIntent()
    }

    private fun getStateFromChange(change: RoomsViewChange): RoomsViewState {
        return when (change) {
            is RoomsViewChange.Loading -> {
                RoomsViewState.Loading
            }

            is RoomsViewChange.LoadedRooms -> {
                RoomsViewState.LoadedRooms(change.rooms, change.status)
            }

            is RoomsViewChange.Error -> {
                RoomsViewState.Error(change.error)
            }
            RoomsViewChange.Idle -> {
                RoomsViewState.Idle
            }
        }
    }

    private fun consumeIntent() {
        viewModelScope.launch {
            userIntent.receiveAsFlow().collect {
                val changes = when (it) {
                    is RoomsViewAction -> interactor.fetch()
                }
                changes.collect { change ->
                    _state.value = getStateFromChange(change)
                }
            }
        }
    }
}