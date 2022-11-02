package com.rivaldy.id.dicoding.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.rivaldy.id.core.utils.EspressoIdlingResource
import com.rivaldy.id.dicoding.R
import com.rivaldy.id.dicoding.ui.home.detailstory.DetailStoryActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/** Created by github.com/im-o on 11/2/2022.  */

@LargeTest
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loadStories_Success() {
        onView(withId(R.id.listDataRV)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.listDataRV)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
    }

    @Test
    fun showDetailStory_Success() {
        Intents.init()
        onView(withId(R.id.listDataRV)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        intended(hasComponent(DetailStoryActivity::class.java.name))
        intended(hasExtraWithKey(DetailStoryActivity.EXTRA_STORY))
        onView(withId(R.id.nameTV)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.descriptionTV)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.dateCreatedTV)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.photoIV)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        pressBack()
    }

    @Test
    fun showMaps_Success() {
        onView(withId(R.id.mapFAB)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.mapFAB)).perform(ViewActions.click())
        onView(withId(R.id.map)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        pressBack()
    }

    @Test
    fun showFormAddStory_Success() {
        onView(withId(R.id.addStoryItem)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.addStoryItem)).perform(ViewActions.click())
        onView(withText(R.string.add_story)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        pressBack()
    }

    @Test
    fun showOptionMenu_Success() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        onView(withText(R.string.language_setting)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withText(R.string.log_out)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        pressBack()
    }
}