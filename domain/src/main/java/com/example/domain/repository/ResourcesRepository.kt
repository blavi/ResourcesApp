package com.example.domain.repository

import com.example.domain.model.Result

interface  ResourcesRepository<T: Any> {
    suspend fun getData(): Result<List<T>>
}