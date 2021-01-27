package com.example.domain.mvi.action

import com.example.domain.model.PersonDetails

sealed class PeopleViewAction: Action {
    object LoadPeople: PeopleViewAction()
    class LoadPersonDetails(val person: PersonDetails): PeopleViewAction()
}
