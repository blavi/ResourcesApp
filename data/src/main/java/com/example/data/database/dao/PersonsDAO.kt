package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.base.PEOPLE_TABLE_NAME
import com.example.data.database.model.PersonEntity

@Dao
interface PersonsDAO {
    @Query("SELECT * FROM $PEOPLE_TABLE_NAME ORDER BY ID")
    suspend fun loadAllPersons(): List<PersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersons(persons: List<PersonEntity>)

    @Query("DELETE FROM $PEOPLE_TABLE_NAME")
    suspend fun deleteAll()
}