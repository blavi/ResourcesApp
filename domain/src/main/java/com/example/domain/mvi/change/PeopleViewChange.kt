package com.example.domain.mvi.change

import com.example.domain.model.PersonDetails

sealed class PeopleViewChange: Change {
    object Idle: PeopleViewChange()
    object Loading: PeopleViewChange()
    data class LoadedPersons(val people: List<PersonDetails>): PeopleViewChange()
    data class Error(val error: String?): PeopleViewChange()
    data class GoToDetails(val person: PersonDetails): PeopleViewChange()
}
