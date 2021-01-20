package com.example.test.di

import android.content.Context
import androidx.room.Room
import com.example.test.BuildConfig
import com.example.test.api.ApiEndpoints
import com.example.test.api.ApiProvider
import com.example.test.api.ApiProviderImpl
import com.example.test.interactor.FetchPeopleInteractor
import com.example.test.interactor.FetchRoomsInteractor
import com.example.test.persistence.EntitiesDatabase
import com.example.test.persistence.PersonsDAO
import com.example.test.persistence.RoomsDAO
import com.example.test.respository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiEndpoints = retrofit.create(ApiEndpoints::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelperImpl: ApiProviderImpl): ApiProvider = apiHelperImpl
//
//    @Provides
//    @Singleton
//    fun provideRepository(repository: Repository): Repository = apiHelperImpl

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
        ).build()
    }

    @Provides
    @Singleton
    fun provideFetchPeopleInteractor(repository: Repository, @ApplicationContext appContext: Context) = FetchPeopleInteractor(repository, appContext)

    @Provides
    @Singleton
    fun provideFetchRoomsInteractor(repository: Repository, @ApplicationContext appContext: Context) = FetchRoomsInteractor(repository, appContext)
}