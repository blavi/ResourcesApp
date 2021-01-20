package com.example.test.api

import com.example.test.model.Person
import com.example.test.model.Room
import retrofit2.Response
import retrofit2.http.GET

interface ApiEndpoints {

    @GET("people")
    suspend fun fetchPeople(): Response<List<Person>>

    @GET("rooms")
    suspend fun fetchRooms(): Response<List<Room>>
}