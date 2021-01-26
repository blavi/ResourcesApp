package com.example.data.di

import com.example.data.respository.PeopleRepositoryImpl
import com.example.data.respository.RoomsRepositoryImpl
import com.example.domain.model.PersonDetails
import com.example.domain.model.RoomDetails
import com.example.domain.repository.ResourcesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPeopleRepository(impl: PeopleRepositoryImpl): ResourcesRepository<PersonDetails>

    @Binds
    abstract fun bindRoomsRepository(impl: RoomsRepositoryImpl): ResourcesRepository<RoomDetails>
}