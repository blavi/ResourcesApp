package com.example.data.network.model

import com.example.data.database.model.RoomEntity
import com.example.data.network.base.RoomMapper

class RoomResponse (
    val id : Int,
    val created_at : String,
    val name : String,
    val max_occupancy : Int,
    val is_occupied : Boolean
): RoomMapper<RoomEntity> {
    override fun mapToRoomEntity(): RoomEntity {
        return RoomEntity(id, created_at, name, max_occupancy, is_occupied)
    }
}