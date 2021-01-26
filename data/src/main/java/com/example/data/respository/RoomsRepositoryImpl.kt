package com.example.data.respository

import com.example.data.database.dao.RoomsDAO
import com.example.data.database.model.RoomEntity
import com.example.data.network.api.ApiEndpoints
import com.example.data.network.base.getData
import com.example.domain.model.Result
import com.example.domain.model.RoomDetails
import com.example.domain.repository.ResourcesRepository
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor(private val roomsDAO: RoomsDAO, private val resourcesApi: ApiEndpoints): BaseRepository<RoomDetails, RoomEntity>(), ResourcesRepository<RoomDetails>  {
    override suspend fun getData(): Result<List<RoomDetails>> {

        return fetchData(
            apiDataProvider = {
                resourcesApi.fetchRooms()
                    .getData(
                        cacheAction = { roomsDAO.insertRooms(it) },
                        fetchFromCacheAction = { roomsDAO.loadAllRooms() }
                    )
            },
            dbDataProvider = { roomsDAO.loadAllRooms() }
        )
    }
}