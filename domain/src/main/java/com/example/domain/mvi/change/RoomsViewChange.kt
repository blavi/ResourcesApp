package com.example.domain.mvi.change

import com.example.domain.model.RoomDetails

sealed class RoomsViewChange: Change() {
    object Idle: RoomsViewChange()
    object Loading: RoomsViewChange()
    data class LoadedRooms(val rooms: List<RoomDetails>): RoomsViewChange()
    data class Error(val error: String?): RoomsViewChange()
}
