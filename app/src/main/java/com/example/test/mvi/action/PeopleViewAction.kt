package com.example.test.mvi.action

import com.example.test.model.Person

sealed class PeopleViewAction {
    object LoadPeople: PeopleViewAction()
    class LoadPersonDetails(val person: Person): PeopleViewAction()
}
