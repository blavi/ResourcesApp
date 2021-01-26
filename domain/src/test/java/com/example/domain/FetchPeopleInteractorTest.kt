package com.example.domain

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.interactor.FetchPeopleInteractor
import com.example.domain.model.Failure
import com.example.domain.model.HttpError
import com.example.domain.model.PersonDetails
import com.example.domain.model.Success
import com.example.domain.mvi.change.PeopleViewChange
import com.example.domain.repository.ResourcesRepository
import com.example.domain.util.GENERAL_NETWORK_ERROR
import com.example.domain.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.equalTo
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FetchPeopleInteractorTest {
    private lateinit var repository: ResourcesRepository<PersonDetails>
    private lateinit var interactor: FetchPeopleInteractor
    private lateinit var context: Context

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        repository = mockk()
        context = mockk()
        interactor = FetchPeopleInteractor(repository)
    }

    @Test
    fun test_FetchPersons_Loading() = mainCoroutineRule.runBlockingTest  {
        val data = ArrayList<PersonDetails>()
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())

        coEvery { repository.getData() } returns Success(data)

        val change = interactor.invoke().first()

        Assert.assertSame(PeopleViewChange.Loading, change)
    }

    @Test
    fun test_FetchPersons_Successful() = mainCoroutineRule.runBlockingTest  {
        val data = ArrayList<PersonDetails>()
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())
        data.add(mockk())

        coEvery { repository.getData() } returns Success(data)

        val change = interactor.invoke().toList()

        Assert.assertThat(
            listOf(PeopleViewChange.Loading, PeopleViewChange.LoadedPersons(data)),
            equalTo(change)
        )
    }

    @Test
    fun test_FetchPersons_FailedRequest_EmptyResult() = mainCoroutineRule.runBlockingTest  {
        coEvery { repository.getData() } returns Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))

        val change = interactor.invoke().toList()

        Assert.assertThat(
            listOf(PeopleViewChange.Loading, PeopleViewChange.Error(GENERAL_NETWORK_ERROR)),
            equalTo(change)
        )
    }
}