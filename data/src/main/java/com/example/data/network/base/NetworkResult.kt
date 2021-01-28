package com.example.data.network.base

import com.example.data.database.base.DB_ENTRY_ERROR
import com.example.domain.model.Failure
import com.example.domain.model.HttpError
import com.example.domain.model.Result
import com.example.domain.model.Success
import com.example.domain.util.GENERAL_NETWORK_ERROR
import retrofit2.Response
import java.io.IOException

interface DomainMapper<T : Any> {
    fun mapToDomainModel(): T
}

interface RoomMapper<out T : Any> {
    fun mapToRoomEntity(): T
}

inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (isSuccessful) body()?.run(action)
    return this
}

inline fun <T : Any> Response<T>.onFailure(action: (HttpError) -> Unit) {
    if (!isSuccessful) errorBody()?.run { action(HttpError(Throwable(message()), code())) }
}

/**
 * Use this if you need to cache data after fetching it from the api, or retrieve something from cache
 */

inline fun <T : RoomMapper<R>, R : DomainMapper<U>, U : Any> Response<List<T>>.getData(
  cacheAction: (List<R>) -> Unit,
  fetchFromCacheAction: () -> List<R>
): Result<List<U>> {
    try {
        onSuccess {
            var entities = emptyList<R>()
            it.forEach { entity ->
                entities = entities.plus(entity.mapToRoomEntity())
            }
            cacheAction(entities)

            var domainEntities = emptyList<U>()
            entities.forEach { entity ->
                domainEntities = domainEntities.plus(entity.mapToDomainModel())
            }
            return Success(domainEntities)
        }
        onFailure {
            val cachedEntities = fetchFromCacheAction()

            var domainEntities = emptyList<U>()
            cachedEntities.forEach { entity ->
                domainEntities = domainEntities.plus(entity.mapToDomainModel())
            }

            if (cachedEntities != null) {
              Success(domainEntities)
            } else  {
              Failure(HttpError(Throwable(DB_ENTRY_ERROR)))
            }
        }
        return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
    } catch (e: IOException) {
        return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
    }
}

/**
 * Use this when communicating only with the api service
 */
fun <T : DomainMapper<R>, R : List<Any>> Response<T>.getData(): Result<R> {
    try {
        onSuccess { return Success(it.mapToDomainModel()) }
        onFailure { return Failure(it) }
        return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
    } catch (e: IOException) {
        return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
    }
}
