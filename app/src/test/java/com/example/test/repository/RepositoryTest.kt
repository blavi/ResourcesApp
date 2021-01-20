package com.example.test.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.test.api.ApiProviderImpl
import com.example.test.model.Person
import com.example.test.model.Room
import com.example.test.persistence.PersonsDAO
import com.example.test.persistence.RoomsDAO
import com.example.test.respository.Repository
import com.example.test.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.hamcrest.Matchers.equalTo
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class RepositoryTest {
    private lateinit var repository: Repository
    private lateinit var personsDAO: PersonsDAO
    private lateinit var roomsDAO: RoomsDAO
    private lateinit var apiHelper: ApiProviderImpl

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        personsDAO = mockk()
        roomsDAO = mockk()
        apiHelper = mockk()
        repository = Repository(personsDAO, roomsDAO, apiHelper)
    }

    @Test
    fun getPeople_Successful() = mainCoroutineRule.runBlockingTest  {
        val data = ArrayList<Person>()
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())

        val result = Response.success(data.toList())

        coEvery { apiHelper.fetchPeople() } returns result

        coEvery { personsDAO.deleteAll() } returns Unit

        coEvery { personsDAO.insertPersons(data) } returns Unit

        Assert.assertThat(result, equalTo(repository.getPeople()))
    }

    @Test
    fun getPeople_Failed() = mainCoroutineRule.runBlockingTest  {
        val body =  ResponseBody.create(
            "application/json".toMediaTypeOrNull(),
            "{\"key\":[\"somestuff\"]}"
        )
        val result: Response<List<Person>> = Response.error(400, body)

        coEvery { apiHelper.fetchPeople() } returns result

        Assert.assertThat(result, equalTo(repository.getPeople()))
    }

    @Test
    fun getRooms_Successful() = mainCoroutineRule.runBlockingTest  {
        val data = ArrayList<Room>()
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())

        val result = Response.success(data.toList())

        coEvery { apiHelper.fetchRooms() } returns result

        coEvery { roomsDAO.deleteAll() } returns Unit

        coEvery { roomsDAO.insertRooms(data) } returns Unit

        Assert.assertThat(result, equalTo(repository.getRooms()))
    }

    @Test
    fun getRooms_Failed() = mainCoroutineRule.runBlockingTest  {
        val body =  ResponseBody.create(
            "application/json".toMediaTypeOrNull(),
            "{\"key\":[\"somestuff\"]}"
        )
        val result: Response<List<Room>> = Response.error(400, body)

        coEvery { apiHelper.fetchRooms() } returns result

        Assert.assertThat(result, equalTo(repository.getRooms()))
    }

}