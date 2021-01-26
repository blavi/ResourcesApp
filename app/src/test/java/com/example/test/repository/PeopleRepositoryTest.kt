package com.example.test.repository

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.example.data.api.ApiProviderImpl
//import com.example.data.database.model.PersonEntity
//import com.example.data.database.model.RoomEntity
//import com.example.data.database.dao.PersonsDAO
//import com.example.data.database.dao.RoomsDAO
//import com.example.data.respository.PeopleRepositoryImpl
//import com.example.data.util.MainCoroutineRule
//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.test.runBlockingTest
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.ResponseBody
//import org.hamcrest.Matchers.equalTo
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import retrofit2.Response
//
//class PeopleRepositoryTest {
//    private lateinit var peopleRepositoryImpl: PeopleRepositoryImpl
//    private lateinit var personsDAO: PersonsDAO
//    private lateinit var roomsDAO: RoomsDAO
//    private lateinit var apiHelper: ApiProviderImpl
//
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()
//
//    @Before
//    fun setup() {
//        personsDAO = mockk()
//        roomsDAO = mockk()
//        apiHelper = mockk()
//        peopleRepositoryImpl = PeopleRepositoryImpl(personsDAO, roomsDAO, apiHelper)
//    }
//
//    @Test
//    fun getPeople_Successful() = mainCoroutineRule.runBlockingTest  {
//        val data = ArrayList<PersonEntity>()
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//
//        val result = Response.success(data.toList())
//
//        coEvery { apiHelper.fetchPeople() } returns result
//
//        coEvery { personsDAO.deleteAll() } returns Unit
//
//        coEvery { personsDAO.insertPersons(data) } returns Unit
//
//        Assert.assertThat(result, equalTo(peopleRepositoryImpl.getPeople()))
//    }
//
//    @Test
//    fun getPeople_Failed() = mainCoroutineRule.runBlockingTest  {
//        val body =  ResponseBody.create(
//            "application/json".toMediaTypeOrNull(),
//            "{\"key\":[\"somestuff\"]}"
//        )
//        val result: Response<List<PersonEntity>> = Response.error(400, body)
//
//        coEvery { apiHelper.fetchPeople() } returns result
//
//        Assert.assertThat(result, equalTo(peopleRepositoryImpl.getPeople()))
//    }
//
//    @Test
//    fun getRooms_Successful() = mainCoroutineRule.runBlockingTest  {
//        val data = ArrayList<RoomEntity>()
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//        data.add(mockk())
//
//        val result = Response.success(data.toList())
//
//        coEvery { apiHelper.fetchRooms() } returns result
//
//        coEvery { roomsDAO.deleteAll() } returns Unit
//
//        coEvery { roomsDAO.insertRooms(data) } returns Unit
//
//        Assert.assertThat(result, equalTo(peopleRepositoryImpl.getRooms()))
//    }
//
//    @Test
//    fun getRooms_Failed() = mainCoroutineRule.runBlockingTest  {
//        val body =  ResponseBody.create(
//            "application/json".toMediaTypeOrNull(),
//            "{\"key\":[\"somestuff\"]}"
//        )
//        val result: Response<List<RoomEntity>> = Response.error(400, body)
//
//        coEvery { apiHelper.fetchRooms() } returns result
//
//        Assert.assertThat(result, equalTo(peopleRepositoryImpl.getRooms()))
//    }
//
//}