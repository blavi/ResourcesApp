package com.example.test.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.network.base.GENERAL_NETWORK_ERROR
import com.example.test.util.MainCoroutineRule
import com.example.domain.interactor.FetchPeopleInteractor
import com.example.domain.model.PersonDetails
import com.example.domain.mvi.action.PeopleViewAction
import com.example.domain.mvi.change.PeopleViewChange
import com.example.domain.mvi.state.PeopleViewState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TestPeopleViewModel {
    private lateinit var viewModel: PeopleViewModel
    private lateinit var peopleService: FetchPeopleInteractor

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        peopleService = mockk<FetchPeopleInteractor>()
        viewModel = PeopleViewModel(peopleService)
    }

    @Test
    fun test_LoadPersons_Successful() = mainCoroutineRule.runBlockingTest  {
        val data = ArrayList<PersonDetails>()
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

        coEvery { peopleService.invoke() } returns flowOf(PeopleViewChange.LoadedPersons(data))

        viewModel.userIntent.send(PeopleViewAction.LoadPeople)

        coVerify { peopleService.invoke() }

        Assert.assertEquals(10, (viewModel.state.first() as PeopleViewState.LoadedPersons).people.size)

        Assert.assertEquals(PeopleViewState.LoadedPersons(data), viewModel.state.first())
    }

    @Test
    fun test_LoadPersons_Error() = mainCoroutineRule.runBlockingTest  {
        coEvery { peopleService.invoke() } returns flowOf(PeopleViewChange.Error(GENERAL_NETWORK_ERROR))

        viewModel.userIntent.send(PeopleViewAction.LoadPeople)

        coVerify { peopleService.invoke() }

        Assert.assertEquals(PeopleViewState.Error(GENERAL_NETWORK_ERROR), viewModel.state.first())
    }
}