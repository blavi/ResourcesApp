package com.example.test.interactor

import android.content.Context
import com.example.test.R
import com.example.test.mvi.change.PeopleViewChange
import com.example.test.respository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchPeopleInteractor @Inject constructor(private val repository: Repository, context: Context): FetchInteractor(context) {

    override suspend fun fetch(): Flow<PeopleViewChange> {
        return flow {
            emit(PeopleViewChange.Loading)
            if (isOnline()) {
                val result = repository.getPeople()

                val change = when (result.code()) {
                    200 -> PeopleViewChange.LoadedPersons(
                        result.body()!!,
                        context.getString(R.string.fetched_from_server)
                    )
                    else -> fetchFromDB()
                }
                emit(change)
            } else {
                emit(fetchFromDB())
            }
        }
    }

    override suspend fun fetchFromDB(): PeopleViewChange {
        val result = repository.getPeopleFromDB()

        return if (result.isEmpty()) {
            PeopleViewChange.Error(context.getString(R.string.fetch_error))
        } else {
            PeopleViewChange.LoadedPersons(result, context.getString(R.string.fetch_from_database))
        }
    }
}