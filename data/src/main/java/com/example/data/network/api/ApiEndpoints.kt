package com.example.data.network.api

import com.example.data.network.model.PersonResponse
import com.example.data.network.model.RoomResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiEndpoints {

    @GET("people")
    suspend fun fetchPeople(): Response<List<PersonResponse>>

    @GET("rooms")
    suspend fun fetchRooms(): Response<List<RoomResponse>>
}