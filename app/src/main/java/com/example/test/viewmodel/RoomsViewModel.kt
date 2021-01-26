package com.example.test.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.FetchInteractor
import com.example.domain.mvi.action.RoomsViewAction
import com.example.domain.mvi.change.RoomsViewChange
import com.example.domain.mvi.state.RoomsViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Named

class RoomsViewModel @ViewModelInject constructor(@Named("RoomsInteractor") private val interactor: FetchInteractor): ViewModel() {
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
                RoomsViewState.LoadedRooms(change.rooms)
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
            userIntent.receiveAsFlow().collect { action ->
                val changes = when (action) {
                    is RoomsViewAction -> interactor.invoke()
                }
                changes.collect { change ->
                    (change as? RoomsViewChange)?.let {
                        _state.value = getStateFromChange(it)
                    }
                }
            }
        }
    }
}