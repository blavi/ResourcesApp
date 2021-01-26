package com.example.domain.interactor

import com.example.domain.base.BaseUseCase
import com.example.domain.mvi.change.Change
import kotlinx.coroutines.flow.Flow

interface FetchInteractor: BaseUseCase<Change> {

    override suspend operator fun invoke(): Flow<Change>
}