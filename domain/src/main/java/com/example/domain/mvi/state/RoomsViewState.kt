package com.example.domain.mvi.state

import com.example.domain.model.RoomDetails

sealed class RoomsViewState: State {
    object Idle: RoomsViewState()
    object Loading: RoomsViewState()
    data class LoadedRooms(val rooms: List<RoomDetails>): RoomsViewState()
    data class Error(val error: String?): RoomsViewState()
}
