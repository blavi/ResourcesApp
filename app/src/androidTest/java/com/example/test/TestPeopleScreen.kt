package com.example.test

import android.app.Instrumentation
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.test.adapter.PeopleAdapter
import com.example.test.ui.DetailsActivity
import com.example.test.ui.PeopleFragment
import com.example.test.util.EspressoIdlingResourceRule
import com.example.test.util.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TestPeopleScreen {

    private val LIST_ITEM_IN_TEST = 2

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Test
    fun test_PeopleFragment_IsDisplayed() {
        launchFragmentInHiltContainer<PeopleFragment> { }

        onView(withId(R.id.peopleProgressBar)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))

        onView(withId(R.id.peopleRecyclerView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_SelectPersonOnPortrait() {
        Intents.init()

        launchFragmentInHiltContainer<PeopleFragment> { }

        val expectedIntent: Matcher<Intent> = allOf(
                hasComponent(DetailsActivity::class.java.name)
        )

        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))

        onView(withId(R.id.peopleRecyclerView))
            .perform(
                    RecyclerViewActions.actionOnItemAtPosition<PeopleAdapter.DataViewHolder>(
                            LIST_ITEM_IN_TEST,
                            ViewActions.click()
                    )
            )

        intended(expectedIntent)

        Intents.release()
    }

    @Test
    fun test_SelectPersonOnLandscape() {
        val activityScenario: ActivityScenario<HiltTestActivity> = ActivityScenario.launch(HiltTestActivity::class.java)

        activityScenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        launchFragmentInHiltContainer<PeopleFragment> { }

        onView(withId(R.id.detailsNavContainer)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        activityScenario.close()
    }
}