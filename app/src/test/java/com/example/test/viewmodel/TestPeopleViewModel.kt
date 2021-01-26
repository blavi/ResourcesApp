package com.example.test.viewmodel
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.example.domain.interactor.FetchPeopleInteractor
//import com.example.data.database.model.PersonEntity
//import com.example.domain.mvi.action.PeopleViewAction
//import com.example.domain.mvi.change.PeopleViewChange
//import com.example.domain.mvi.state.PeopleViewState
//import com.example.data.util.MainCoroutineRule
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//class TestPeopleViewModel {
//    private lateinit var viewModel: PeopleViewModel
//    private lateinit var peopleService: FetchPeopleInteractor
//
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()
//
//    @Before
//    fun setup() {
//        peopleService = mockk<FetchPeopleInteractor>()
//        viewModel = PeopleViewModel(peopleService)
//    }
//
//    @Test
//    fun test_LoadPersons_Successful() = mainCoroutineRule.runBlockingTest  {
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
//        coEvery { peopleService.fetch() } returns flowOf(PeopleViewChange.LoadedPersons(data, ""))
//
//        viewModel.userIntent.send(PeopleViewAction.LoadPeople)
//
//        coVerify { peopleService.fetch() }
//
//        Assert.assertEquals(10, (viewModel.state.first() as PeopleViewState.LoadedPersons).people.size)
//
//        Assert.assertEquals(PeopleViewState.LoadedPersons(data, ""), viewModel.state.first())
//    }
//
//    @Test
//    fun test_LoadPersons_Error() = mainCoroutineRule.runBlockingTest  {
//        coEvery { peopleService.fetch() } returns flowOf(PeopleViewChange.Error("error"))
//
//        viewModel.userIntent.send(PeopleViewAction.LoadPeople)
//
//        coVerify { peopleService.fetch() }
//
//        Assert.assertEquals(PeopleViewState.Error("error"), viewModel.state.first())
//    }
//}