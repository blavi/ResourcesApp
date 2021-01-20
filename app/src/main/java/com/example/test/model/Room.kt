package com.example.test.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "room")
data class Room(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id") val id : Int,

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at") val created_at : String,

    @ColumnInfo(name = "name")
    @SerializedName("name") val name : String,

    @ColumnInfo(name = "max_occupancy")
    @SerializedName("max_occupancy") val max_occupancy : Int,

    @ColumnInfo(name = "is_occupied")
    @SerializedName("is_occupied") val is_occupied : Boolean
)
