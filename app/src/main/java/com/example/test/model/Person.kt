package com.example.test.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "person")
data class Person(
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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(avatar)
        parcel.writeString(phone)
        parcel.writeString(firstName)
        parcel.writeDouble(longitude)
        parcel.writeString(favouriteColor)
        parcel.writeString(email)
        parcel.writeString(jobTitle)
        parcel.writeString(createdAt)
        parcel.writeDouble(latitude)
        parcel.writeString(lastName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}
