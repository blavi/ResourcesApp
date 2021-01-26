package com.example.domain.di

import com.example.domain.interactor.FetchInteractor
import com.example.domain.interactor.FetchPeopleInteractor
import com.example.domain.interactor.FetchRoomsInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
abstract class InteractorModule {

    @Binds
    @Named("PeopleInteractor")
    abstract fun bindPeopleInteractor(impl: FetchPeopleInteractor): FetchInteractor

    @Binds
    @Named("RoomsInteractor")
    abstract fun bindRoomsInteractor(impl: FetchRoomsInteractor): FetchInteractor
}