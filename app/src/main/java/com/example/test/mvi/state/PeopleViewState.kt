package com.example.test.mvi.state

import com.example.test.model.Person

sealed class PeopleViewState {
    object Idle: PeopleViewState()
    object Loading: PeopleViewState()
    data class LoadedPersons(val people: List<Person>, val status: String): PeopleViewState()
    data class Error(val error: String?): PeopleViewState()
    data class GoToDetails(val person: Person): PeopleViewState()
}
