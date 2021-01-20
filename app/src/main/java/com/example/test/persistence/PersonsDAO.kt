package com.example.test.persistence

import androidx.room.*
import com.example.test.model.Person

@Dao
interface PersonsDAO {
    @Query("SELECT * FROM PERSON ORDER BY ID")
    suspend fun loadAllPersons(): List<Person>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersons(persons: List<Person>)

    @Query("DELETE FROM PERSON")
    suspend fun deleteAll()
}