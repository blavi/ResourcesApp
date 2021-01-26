package com.example.domain.interactor

import com.example.domain.model.onFailure
import com.example.domain.model.onSuccess
import com.example.domain.mvi.change.PeopleViewChange
import com.example.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchPeopleInteractor @Inject constructor(private val repository: PeopleRepository): FetchInteractor{

    override suspend fun invoke(): Flow<PeopleViewChange> {
        return flow {
            emit(PeopleViewChange.Loading)

            repository.getPeople()
                    .onSuccess {
                        emit(PeopleViewChange.LoadedPersons(it))
                    }.onFailure {
                        emit(PeopleViewChange.Error(it.toString()))
                    }
        }
    }
}