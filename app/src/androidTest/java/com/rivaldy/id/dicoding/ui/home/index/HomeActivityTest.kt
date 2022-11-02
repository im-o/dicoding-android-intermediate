package com.rivaldy.id.dicoding.ui.home.index

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.rivaldy.id.core.di.NetworkModule
import com.rivaldy.id.core.utils.EspressoIdlingResource
import com.rivaldy.id.dicoding.R
import com.rivaldy.id.dicoding.util.JsonConverter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/** Created by github.com/im-o on 11/1/2022.  */

@MediumTest
@HiltAndroidTest
class HomeActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer.start(1337)
        NetworkModule.baseUrl = "http://127.0.0.1:1337/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loadStories_Success() {
        ActivityScenario.launch(HomeActivity::class.java)

        val mockResponse = MockResponse().setResponseCode(200).setBody(JsonConverter.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.listDataRV)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.listDataRV)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4))
        onView(withText("Rivaldy")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}