package com.example.test.viewmodel

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.example.domain.interactor.FetchRoomsInteractor
//import com.example.data.database.model.RoomEntity
//import com.example.domain.mvi.action.RoomsViewAction
//import com.example.domain.mvi.change.RoomsViewChange
//import com.example.domain.mvi.state.RoomsViewState
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
//class TestRoomsViewModel {
//    private lateinit var viewModel: RoomsViewModel
//    private lateinit var roomsServices: FetchRoomsInteractor
//
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()
//
//    @Before
//    fun setup() {
//        roomsServices = mockk<FetchRoomsInteractor>()
//        viewModel = RoomsViewModel(roomsServices)
//    }
//
//    @Test
//    fun test_LoadRooms_Successful() = mainCoroutineRule.runBlockingTest  {
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
//        coEvery { roomsServices.fetch() } returns flowOf(RoomsViewChange.LoadedRooms(data, ""))
//
//        viewModel.userIntent.send(RoomsViewAction.LoadRooms)
//
//        coVerify { roomsServices.fetch() }
//
//        Assert.assertEquals(10, (viewModel.state.first() as RoomsViewState.LoadedRooms).rooms.size)
//
//        Assert.assertEquals(RoomsViewState.LoadedRooms(data, ""), viewModel.state.first())
//    }
//
//    @Test
//    fun test_LoadRooms_Error() = mainCoroutineRule.runBlockingTest  {
//        coEvery { roomsServices.fetch() } returns flowOf(RoomsViewChange.Error("error"))
//
//        viewModel.userIntent.send(RoomsViewAction.LoadRooms)
//
//        coVerify { roomsServices.fetch() }
//
//        Assert.assertEquals(RoomsViewState.Error("error"), viewModel.state.first() )
//    }
//}