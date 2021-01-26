package com.example.data.di

import android.content.Context
import com.example.data.common.Connectivity
import com.example.data.common.ConnectivityImpl
import com.example.data.common.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class CommonModule {

    @Provides
    @Singleton
    fun provideConnectivity(@ApplicationContext appContext: Context): Connectivity {
        return ConnectivityImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideCoroutineContextProvider(): CoroutineContextProvider {
        return CoroutineContextProvider()
    }
}