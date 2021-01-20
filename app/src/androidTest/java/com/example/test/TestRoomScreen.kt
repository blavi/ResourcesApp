package com.example.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.test.ui.RoomsFragment
import com.example.test.util.EspressoIdlingResourceRule
import com.example.test.util.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TestRoomScreen {

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Test
    fun test_RoomFragment_IsDisplayed() {
        launchFragmentInHiltContainer<RoomsFragment> { }

        onView(withId(R.id.roomsProgressBar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.roomsRecyclerView)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }
}