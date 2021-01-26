package com.example.data.respository

import com.example.data.common.Connectivity
import com.example.data.common.CoroutineContextProvider
import com.example.data.database.base.DB_ENTRY_ERROR
import com.example.data.network.base.DomainMapper
import com.example.data.network.base.GENERAL_NETWORK_ERROR
import com.example.domain.model.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class BaseRepository<T : Any, R : DomainMapper<T>> {
    @Inject
    lateinit var connectivity: Connectivity

    @Inject
    lateinit var contextProvider: CoroutineContextProvider

    /**
     * Use this if you need to cache data after fetching it from the api, or retrieve something from cache
     */
    protected suspend fun fetchData(apiDataProvider: suspend () -> Result<List<T>>, dbDataProvider: suspend () -> List<R>): Result<List<T>> {
        return if (connectivity.hasNetworkAccess()) {
            withContext(contextProvider.io) {
                apiDataProvider()
            }
        } else {
            withContext(contextProvider.io) {
                val dbResult = dbDataProvider()
                var mappedDbResult = emptyList<T>()
                dbResult.forEach {
                    mappedDbResult = mappedDbResult.plus(it.mapToDomainModel())
                }
                if (dbResult != null) {
                    Success(mappedDbResult)
                }else {
                    Failure(HttpError(Throwable(DB_ENTRY_ERROR)))
                }
            }
        }
    }

    /**
     * Use this when communicating only with the api service
     */
    protected suspend fun fetchData(dataProvider: () -> Result<T>): Result<T> {
        return if (connectivity.hasNetworkAccess()) {
            withContext(contextProvider.io) {
                dataProvider()
            }
        } else {
            Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
        }
    }
}