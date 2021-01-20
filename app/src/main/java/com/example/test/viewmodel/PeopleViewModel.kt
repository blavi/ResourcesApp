package com.example.test.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.interactor.FetchInteractor
import com.example.test.interactor.FetchPeopleInteractor
import com.example.test.mvi.action.PeopleViewAction
import com.example.test.mvi.change.PeopleViewChange
import com.example.test.mvi.state.PeopleViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PeopleViewModel @ViewModelInject constructor(private val interactor: FetchPeopleInteractor): ViewModel() {

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
                PeopleViewState.LoadedPersons(change.people, change.status)
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
            userIntent.receiveAsFlow().collect {
                val changes = when (it) {
                    is PeopleViewAction.LoadPeople -> interactor.fetch()
                    is PeopleViewAction.LoadPersonDetails -> flowOf(PeopleViewChange.GoToDetails(it.person))
                }
                changes.collect { change ->
                    _state.value = getStateFromChange(change)
                }
            }
        }
    }

//    fun retrievePersons() {
//        viewModelScope.launch {
//            _state.value = PeopleViewState.Loading
//
//            _state.value = getStateFromChange(interactor.fetch())
//        }
//    }
}