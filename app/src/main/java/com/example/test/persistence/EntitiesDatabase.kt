package com.example.test.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test.model.Person
import com.example.test.model.Room

@Database(entities = [Person::class, Room::class], version = 1, exportSchema = false)
abstract class EntitiesDatabase : RoomDatabase() {

    abstract fun getPersonsDAO(): PersonsDAO

    abstract fun getRoomsDAO(): RoomsDAO
}