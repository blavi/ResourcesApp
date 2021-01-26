package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.base.ROOMS_TABLE_NAME
import com.example.data.network.base.DomainMapper
import com.example.domain.model.RoomDetails
import com.google.gson.annotations.SerializedName

@Entity(tableName = ROOMS_TABLE_NAME)
data class RoomEntity(
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
): DomainMapper<RoomDetails> {
    override fun mapToDomainModel(): RoomDetails {
        return RoomDetails(name, max_occupancy, is_occupied)
    }
}
