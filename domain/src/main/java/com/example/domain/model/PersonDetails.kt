package com.example.domain.model

import android.os.Parcel
import android.os.Parcelable

data class PersonDetails(
    val avatar : String,
    val phone : String,
    val firstName : String,
    val longitude : Double,
    val favouriteColor : String,
    val email : String,
    val jobTitle : String,
    val latitude : Double,
    val lastName : String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(avatar)
        parcel.writeString(phone)
        parcel.writeString(firstName)
        parcel.writeDouble(longitude)
        parcel.writeString(favouriteColor)
        parcel.writeString(email)
        parcel.writeString(jobTitle)
        parcel.writeDouble(latitude)
        parcel.writeString(lastName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PersonDetails> {
        override fun createFromParcel(parcel: Parcel): PersonDetails {
            return PersonDetails(parcel)
        }

        override fun newArray(size: Int): Array<PersonDetails?> {
            return arrayOfNulls(size)
        }
    }
}
