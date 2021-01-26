package com.example.domain.repository

import com.example.domain.model.Result
import com.example.domain.model.RoomDetails

interface RoomsRepository {
    suspend fun getRooms(): Result<List<RoomDetails>>
}