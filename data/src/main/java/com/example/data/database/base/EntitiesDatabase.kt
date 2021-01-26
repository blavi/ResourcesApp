package com.example.data.database.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.PersonsDAO
import com.example.data.database.dao.RoomsDAO
import com.example.data.database.model.PersonEntity
import com.example.data.database.model.RoomEntity

@Database(entities = [PersonEntity::class, RoomEntity::class], version = 1, exportSchema = false)
abstract class EntitiesDatabase : RoomDatabase() {

    abstract fun getPersonsDAO(): PersonsDAO

    abstract fun getRoomsDAO(): RoomsDAO
}