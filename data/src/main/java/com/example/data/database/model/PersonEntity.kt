package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.base.PEOPLE_TABLE_NAME
import com.example.data.network.base.DomainMapper
import com.example.domain.model.PersonDetails
import com.google.gson.annotations.SerializedName

@Entity(tableName = PEOPLE_TABLE_NAME)
data class PersonEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id") val id : Int,

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar") val avatar : String,

    @ColumnInfo(name = "phone")
    @SerializedName("phone") val phone : String,

    @ColumnInfo(name = "firstName")
    @SerializedName("firstName") val firstName : String,

    @ColumnInfo(name = "longitude")
    @SerializedName("longitude") val longitude : Double,

    @ColumnInfo(name = "favouriteColor")
    @SerializedName("favouriteColor") val favouriteColor : String,

    @ColumnInfo(name = "email")
    @SerializedName("email") val email : String,

    @ColumnInfo(name = "jobTitle")
    @SerializedName("jobTitle") val jobTitle : String,

    @ColumnInfo(name = "createdAt")
    @SerializedName("createdAt") val createdAt : String,

    @ColumnInfo(name = "latitude")
    @SerializedName("latitude") val latitude : Double,

    @ColumnInfo(name = "lastName")
    @SerializedName("lastName") val lastName : String
): DomainMapper<PersonDetails> {
    override fun mapToDomainModel(): PersonDetails {
        return PersonDetails(avatar, phone, firstName, longitude, favouriteColor, email, jobTitle, latitude, lastName)
    }
}