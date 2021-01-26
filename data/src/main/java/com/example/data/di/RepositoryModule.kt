package com.example.data.di

import com.example.data.respository.PeopleRepositoryImpl
import com.example.data.respository.RoomsRepositoryImpl
import com.example.domain.repository.PeopleRepository
import com.example.domain.repository.RoomsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPeopleRepository(impl: PeopleRepositoryImpl): PeopleRepository

    @Binds
    abstract fun bindRoomsRepository(impl: RoomsRepositoryImpl): RoomsRepository
}