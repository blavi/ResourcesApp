package com.example.test.mvi.change

import com.example.test.model.Room

sealed class RoomsViewChange: Change() {
    object Idle: RoomsViewChange()
    object Loading: RoomsViewChange()
    data class LoadedRooms(val rooms: List<Room>, val status: String): RoomsViewChange()
    data class Error(val error: String?): RoomsViewChange()
}
