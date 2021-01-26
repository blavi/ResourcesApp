package com.example.data.respository

import com.example.data.database.dao.PersonsDAO
import com.example.data.database.model.PersonEntity
import com.example.data.network.api.ApiEndpoints
import com.example.data.network.base.getData
import com.example.domain.model.PersonDetails
import com.example.domain.model.Result
import com.example.domain.repository.PeopleRepository
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(private val personsDAO: PersonsDAO, private val resourcesApi: ApiEndpoints): BaseRepository<PersonDetails, PersonEntity>(), PeopleRepository  {

    override suspend fun getPeople(): Result<List<PersonDetails>> {

        return fetchData(
            apiDataProvider = {
                resourcesApi.fetchPeople()
                    .getData(
                        cacheAction = { personsDAO.insertPersons(it) },
                        fetchFromCacheAction = { personsDAO.loadAllPersons() }
                    )
            },
            dbDataProvider = { personsDAO.loadAllPersons() }
        )
    }
}
