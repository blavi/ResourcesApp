package com.example.domain.interactor

import com.example.domain.model.RoomDetails
import com.example.domain.model.onFailure
import com.example.domain.model.onSuccess
import com.example.domain.mvi.change.RoomsViewChange
import com.example.domain.repository.ResourcesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchRoomsInteractor @Inject constructor(private val repository: ResourcesRepository<RoomDetails>): FetchInteractor {

    override suspend fun invoke(): Flow<RoomsViewChange> {
        return flow {
            emit(RoomsViewChange.Loading)

            repository.getData()
                .onSuccess {
                    emit(RoomsViewChange.LoadedRooms(it))
                }.onFailure {
                    emit(RoomsViewChange.Error(it.throwable.message))
                }
        }
    }
}