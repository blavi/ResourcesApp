package com.example.test.interactor

import android.content.Context
import com.example.test.mvi.change.RoomsViewChange
import com.example.test.respository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchRoomsInteractor @Inject constructor(private val repository: Repository, context: Context): FetchInteractor(context) {

    override suspend fun fetch(): Flow<RoomsViewChange> {
        return flow {
            if (isOnline()) {
                emit(RoomsViewChange.Loading)

                val result = repository.getRooms()

                val change = when (result.code()) {
                        200 -> RoomsViewChange.LoadedRooms(result.body()!!, "Data from server")
                        else -> RoomsViewChange.Error(result.message())
                    }

                emit(change)
            } else {
                emit(fetchFromDB())
            }
        }
    }

    override suspend fun fetchFromDB(): RoomsViewChange {
        val result = repository.getRoomsFromDB()

        return if (result.isEmpty()) {
            RoomsViewChange.Error("No data could be retrieved.")
        } else {
            RoomsViewChange.LoadedRooms(result, "Cached data")
        }
    }
}