package com.example.domain

//import android.content.Context
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.example.test.R
//import com.example.test.model.Person
//import com.example.test.mvi.change.PeopleViewChange
//import com.example.data.respository.Repository
//import com.example.test.util.MainCoroutineRule
//import io.mockk.coEvery
//import io.mockk.every
//import io.mockk.mockk
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.toList
//import kotlinx.coroutines.test.runBlockingTest
//import org.hamcrest.Matchers.equalTo
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import retrofit2.Response
//
//class FetchPeopleInteractorTest {
//    private lateinit var repository: Repository
//    private lateinit var interactor: FetchPeopleInteractor
//    private lateinit var context: Context
//
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()
//
//    @Before
//    fun setup() {
//        repository = mockk()
//        context = mockk()
//        interactor = FetchPeopleInteractor(repository, context)
//    }
//
//    @Test
//    fun test_FetchPersons_Loading() = mainCoroutineRule.runBlockingTest  {
//        val data = ArrayList<Person>()
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
//        coEvery { repository.getPeople() } returns Response.success(data)
//
//        coEvery { interactor.isOnline() } returns true
//
//        val change = interactor.fetch().first()
//
//        Assert.assertSame(PeopleViewChange.Loading, change)
//    }
//
//    @Test
//    fun test_FetchPersons_Successful() = mainCoroutineRule.runBlockingTest  {
//        val data = ArrayList<Person>()
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
//        coEvery { repository.getPeople() } returns Response.success(data)
//
//        coEvery { interactor.isOnline() } returns true
//
//        every { context.getString(R.string.fetched_from_server) } returns "Data fetched from server."
//
//        val change = interactor.fetch().toList()
//
//        Assert.assertThat(
//            listOf(PeopleViewChange.Loading, PeopleViewChange.LoadedPersons(data, "Data fetched from server.")),
//            equalTo(change)
//        )
//    }
//
//    @Test
//    fun test_FetchPersons_NoInternetConnection_EmptyResult() = mainCoroutineRule.runBlockingTest  {
//        coEvery { repository.getPeopleFromDB() } returns emptyList()
//
//        coEvery { interactor.isOnline() } returns false
//
//        every { context.getString(R.string.fetch_error) } returns "No data could be retrieved."
//
//        val change = interactor.fetch().toList()
//
//        Assert.assertThat(
//            listOf(PeopleViewChange.Loading, PeopleViewChange.Error("No data could be retrieved.")),
//            equalTo(change)
//        )
//    }
//
//    @Test
//    fun test_FetchPersons_NoInternetConnection_ResultFromDB() = mainCoroutineRule.runBlockingTest  {
//        val data = ArrayList<Person>()
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
//        coEvery { repository.getPeopleFromDB() } returns data
//
//        coEvery { interactor.isOnline() } returns false
//
//        every { context.getString(R.string.fetch_from_database) } returns "Cached data."
//
//        val change = interactor.fetch().toList()
//
//        Assert.assertThat(
//            listOf(PeopleViewChange.Loading, PeopleViewChange.LoadedPersons(data, "Cached data.")),
//            equalTo(change)
//        )
//    }
//}