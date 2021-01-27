package com.example.domain.mvi.action

sealed class RoomsViewAction: Action {
    object LoadRooms : RoomsViewAction()
}
