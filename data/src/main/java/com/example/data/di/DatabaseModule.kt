package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.base.EntitiesDatabase
import com.example.data.database.dao.PersonsDAO
import com.example.data.database.dao.RoomsDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun providePersonsDAO(appDatabase: EntitiesDatabase): PersonsDAO {
        return appDatabase.getPersonsDAO()
    }

    @Provides
    @Singleton
    fun provideRoomsDAO(appDatabase: EntitiesDatabase): RoomsDAO {
        return appDatabase.getRoomsDAO()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): EntitiesDatabase {
        return Room.databaseBuilder(
            appContext,
            EntitiesDatabase::class.java,
            "EntitiesDatabase"
        ).fallbackToDestructiveMigration().build()
    }
}