package com.example.data.database.dao

import androidx.room.*
import com.example.data.database.base.ROOMS_TABLE_NAME
import com.example.data.database.model.RoomEntity

@Dao
interface RoomsDAO {
    @Query("SELECT * FROM $ROOMS_TABLE_NAME ORDER BY ID")
    suspend fun loadAllRooms(): List<RoomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRooms(rooms: List<RoomEntity>)

    @Query("DELETE FROM $ROOMS_TABLE_NAME")
    suspend fun deleteAll()
}