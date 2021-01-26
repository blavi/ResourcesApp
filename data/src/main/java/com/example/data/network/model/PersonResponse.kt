package com.example.data.network.model

import com.example.data.database.model.PersonEntity
import com.example.data.network.base.RoomMapper

data class PersonResponse(
    val id : Int,
    val avatar : String,
    val phone : String,
    val firstName : String,
    val longitude : Double,
    val favouriteColor : String,
    val email : String,
    val jobTitle : String,
    val createdAt : String,
    val latitude : Double, val lastName : String
    ): RoomMapper<PersonEntity> {
    override fun mapToRoomEntity(): PersonEntity {
        return PersonEntity(id, avatar, phone, firstName, longitude, favouriteColor, email, jobTitle, createdAt, latitude, lastName)
    }
}
