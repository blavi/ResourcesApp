package com.example.domain.repository

import com.example.domain.model.PersonDetails
import com.example.domain.model.Result

interface PeopleRepository {
  suspend fun getPeople(): Result<List<PersonDetails>>
}