package com.example.test.api

import com.example.test.model.Person
import com.example.test.model.Room
import retrofit2.Response

interface ApiProvider {
    suspend fun fetchPeople(): Response<List<Person>>

    suspend fun fetchRooms(): Response<List<Room>>
}