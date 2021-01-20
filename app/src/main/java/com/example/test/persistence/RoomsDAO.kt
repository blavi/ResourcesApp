package com.example.test.persistence

import androidx.room.*
import com.example.test.model.Room

@Dao
interface RoomsDAO {
    @Query("SELECT * FROM ROOM ORDER BY ID")
    suspend fun loadAllRooms(): List<Room>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRooms(rooms: List<Room>)

    @Query("DELETE FROM ROOM")
    suspend fun deleteAll()
}