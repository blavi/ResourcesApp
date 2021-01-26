package com.example.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.common.Connectivity
import com.example.data.common.TestCoroutineContextProvider
import com.example.data.database.dao.PersonsDAO
import com.example.data.database.model.PersonEntity
import com.example.data.network.api.ApiEndpoints
import com.example.data.network.model.PersonResponse
import com.example.data.respository.PeopleRepositoryImpl
import com.example.data.util.MainCoroutineRule
import com.example.domain.model.PersonDetails
import com.example.domain.model.Success
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.equalTo
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class PeopleRepositoryImplTest {
    private lateinit var repository: PeopleRepositoryImpl
    private lateinit var personsDAO: PersonsDAO
    private lateinit var api: ApiEndpoints
    private lateinit var connectivity: Connectivity
    private lateinit var contextProvider: TestCoroutineContextProvider

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        api = mockk()
        personsDAO = mockk()
        connectivity = mockk()
        contextProvider = TestCoroutineContextProvider()
        repository = PeopleRepositoryImpl(personsDAO, api)
        repository.connectivity = connectivity
        repository.contextProvider = contextProvider
    }

    @Test
    fun test_FetchPersons_FromAPI() = mainCoroutineRule.runBlockingTest  {

        val personR = mockk<PersonResponse>()
        val personE = mockk<PersonEntity>()
        val personD = mockk<PersonDetails>()

        every { personR.mapToRoomEntity() } returns personE
        every { personE.mapToDomainModel() } returns personD

        val data = ArrayList<PersonResponse>()
        data.add(personR)
        data.add(personR)
        data.add(personR)

        val peopleEntities = ArrayList<PersonEntity>()
        peopleEntities.add(personE)
        peopleEntities.add(personE)
        peopleEntities.add(personE)

        val peopleResult = ArrayList<PersonDetails>()
        peopleResult.add(personD)
        peopleResult.add(personD)
        peopleResult.add(personD)

        every { connectivity.hasNetworkAccess() } returns true

        coEvery { api.fetchPeople() } returns Response.success(data.toList())
        coEvery { personsDAO.insertPersons(peopleEntities) } returns Unit

        Assert.assertThat(Success(peopleResult), equalTo(repository.getData()))
    }

    @Test
    fun test_FetchPersons_FromDB() = mainCoroutineRule.runBlockingTest  {

        val personR = mockk<PersonResponse>()
        val personE = mockk<PersonEntity>()
        val personD = mockk<PersonDetails>()

        every { personR.mapToRoomEntity() } returns personE
        every { personE.mapToDomainModel() } returns personD

        val data = ArrayList<PersonResponse>()
        data.add(personR)
        data.add(personR)
        data.add(personR)

        val peopleEntities = ArrayList<PersonEntity>()
        peopleEntities.add(personE)
        peopleEntities.add(personE)
        peopleEntities.add(personE)

        val peopleResult = ArrayList<PersonDetails>()
        peopleResult.add(personD)
        peopleResult.add(personD)
        peopleResult.add(personD)

        every { connectivity.hasNetworkAccess() } returns false

        coEvery { api.fetchPeople() } returns Response.success(data.toList())
        coEvery { personsDAO.loadAllPersons() } returns peopleEntities

        Assert.assertThat(Success(peopleResult), equalTo(repository.getData()))
    }
}