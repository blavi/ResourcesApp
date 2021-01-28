package com.example.test.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.interactor.FetchRoomsInteractor
import com.example.domain.model.RoomDetails
import com.example.domain.mvi.action.RoomsViewAction
import com.example.domain.mvi.change.RoomsViewChange
import com.example.domain.mvi.state.RoomsViewState
import com.example.domain.util.GENERAL_NETWORK_ERROR
import com.example.test.util.MainCoroutineRule
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

class TestRoomsViewModel {
    private lateinit var viewModel: RoomsViewModel
    private lateinit var roomsServices: FetchRoomsInteractor

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        roomsServices = mockk<FetchRoomsInteractor>()
        viewModel = RoomsViewModel(roomsServices)
    }

    @Test
    fun test_LoadRooms_Successful() = mainCoroutineRule.runBlockingTest  {
        val data = ArrayList<RoomDetails>()
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

        coEvery { roomsServices.invoke() } returns flowOf(RoomsViewChange.LoadedRooms(data))

        viewModel.userIntent.send(RoomsViewAction.LoadRooms)

        coVerify { roomsServices.invoke() }

        Assert.assertEquals(10, (viewModel.state.first() as RoomsViewState.LoadedRooms).rooms.size)

        Assert.assertEquals(RoomsViewState.LoadedRooms(data), viewModel.state.first())
    }

    @Test
    fun test_LoadRooms_Error() = mainCoroutineRule.runBlockingTest  {
        coEvery { roomsServices.invoke() } returns flowOf(RoomsViewChange.Error(GENERAL_NETWORK_ERROR))

        viewModel.userIntent.send(RoomsViewAction.LoadRooms)

        coVerify { roomsServices.invoke() }

        Assert.assertEquals(RoomsViewState.Error(GENERAL_NETWORK_ERROR), viewModel.state.first() )
    }
}