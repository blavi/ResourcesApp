package com.example.domain.mvi.state

import com.example.domain.model.PersonDetails

sealed class PeopleViewState {
    object Idle: PeopleViewState()
    object Loading: PeopleViewState()
    data class LoadedPersons(val people: List<PersonDetails>): PeopleViewState()
    data class Error(val error: String?): PeopleViewState()
    data class GoToDetails(val person: PersonDetails): PeopleViewState()
}
