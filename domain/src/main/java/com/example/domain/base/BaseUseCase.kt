package com.example.domain.base

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<R: Any> {
  suspend operator fun invoke(): Flow<R>
}