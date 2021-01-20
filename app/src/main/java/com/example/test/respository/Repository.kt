package com.example.test.respository

import com.example.test.api.ApiProviderImpl
import com.example.test.model.Person
import com.example.test.model.Room
import com.example.test.persistence.PersonsDAO
import com.example.test.persistence.RoomsDAO
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val personsDAO: PersonsDAO, private val roomsDAO: RoomsDAO, private val apiHelperImpl: ApiProviderImpl) {
    suspend fun getPeople(): Response<List<Person>> {
        val response = apiHelperImpl.fetchPeople()

        if (response.isSuccessful && !response.body().isNullOrEmpty()) {
            savePeopleToDB(response.body()!!)
        }

        return response
    }

    suspend fun getPeopleFromDB() = personsDAO.loadAllPersons()

    suspend fun getRooms(): Response<List<Room>>  {
        val response = apiHelperImpl.fetchRooms()

        if (response.isSuccessful && !response.body().isNullOrEmpty()) {
            saveRoomsToDB(response.body()!!)
        }

        return response
    }

    suspend fun getRoomsFromDB() = roomsDAO.loadAllRooms()

    private suspend fun savePeopleToDB(people: List<Person>) {
        personsDAO.deleteAll()
        personsDAO.insertPersons(people)
    }

    private suspend fun saveRoomsToDB(rooms: List<Room>) {
        roomsDAO.deleteAll()
        roomsDAO.insertRooms(rooms)
    }
}