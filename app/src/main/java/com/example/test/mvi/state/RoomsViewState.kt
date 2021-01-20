package com.example.test.mvi.state

import com.example.test.model.Room

sealed class RoomsViewState {
    object Idle: RoomsViewState()
    object Loading: RoomsViewState()
    data class LoadedRooms(val rooms: List<Room>, val status: String): RoomsViewState()
    data class Error(val error: String?): RoomsViewState()
}
