package com.example.test.api

import javax.inject.Inject

class ApiProviderImpl @Inject constructor(private val api: ApiEndpoints): ApiProvider {

    override suspend fun fetchPeople() = api.fetchPeople()

    override suspend fun fetchRooms() = api.fetchRooms()
}