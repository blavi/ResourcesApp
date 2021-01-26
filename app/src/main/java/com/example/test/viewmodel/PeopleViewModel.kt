package com.example.test.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.FetchInteractor
import com.example.domain.mvi.action.PeopleViewAction
import com.example.domain.mvi.change.PeopleViewChange
import com.example.domain.mvi.state.PeopleViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Named

class PeopleViewModel @ViewModelInject constructor(@Named("PeopleInteractor") private val interactor: FetchInteractor): ViewModel() {

    val userIntent = Channel<PeopleViewAction>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<PeopleViewState>(PeopleViewState.Idle)

    val state: StateFlow<PeopleViewState>
        get() = _state

    init {
        consumeIntent()
    }

    private fun getStateFromChange(change: PeopleViewChange): PeopleViewState {
        return when (change) {
            is PeopleViewChange.Loading -> {
                PeopleViewState.Loading
            }

            is PeopleViewChange.LoadedPersons -> {
                PeopleViewState.LoadedPersons(change.people)
            }

            is PeopleViewChange.Error -> {
                PeopleViewState.Error(change.error)
            }
            PeopleViewChange.Idle -> {
                PeopleViewState.Idle
            }
            is PeopleViewChange.GoToDetails -> {
                PeopleViewState.GoToDetails(change.person)
            }
        }
    }

    private fun consumeIntent() {
        viewModelScope.launch {
            userIntent.receiveAsFlow().collect { action ->
                val changes = when (action) {
                    is PeopleViewAction.LoadPeople -> interactor.invoke()
                    is PeopleViewAction.LoadPersonDetails -> flowOf(PeopleViewChange.GoToDetails(action.person))
                }
                changes.collect { change ->
                    (change as? PeopleViewChange)?.let {
                        _state.value = getStateFromChange(it)
                    }
                }
            }
        }
    }
}