package com.example.domain.mvi.action

import com.example.domain.model.PersonDetails

sealed class PeopleViewAction {
    object LoadPeople: PeopleViewAction()
    class LoadPersonDetails(val person: PersonDetails): PeopleViewAction()
}
