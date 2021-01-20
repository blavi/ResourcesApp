package com.example.test.mvi.change

import com.example.test.model.Person
import com.example.test.mvi.state.PeopleViewState

sealed class PeopleViewChange: Change() {
    object Idle: PeopleViewChange()
    object Loading: PeopleViewChange()
    data class LoadedPersons(val people: List<Person>, val status: String): PeopleViewChange()
    data class Error(val error: String?): PeopleViewChange()
    data class GoToDetails(val person: Person): PeopleViewChange()
}
