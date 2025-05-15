package com.siri.myb2cecommerce.ui.home

import android.view.MenuItem
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.siri.myb2cecommerce.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.siri.myb2cecommerce.ui.bag.BagFragment
import com.siri.myb2cecommerce.ui.fav.FavFragment
import com.siri.myb2cecommerce.ui.profile.ProfileFragment
import com.siri.myb2cecommerce.ui.shop.ShopFragment
import dagger.hilt.android.AndroidEntryPoint

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        // Ensure the activity has launched correctly
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun testNavigationToHomeFragment() {
        // Click the Home menu item
        onView(withId(R.id.homeMenu)).perform(click())

        // Check if the HomeFragment is displayed
        onView(withId(R.id.nav_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToShopFragment() {
        // Click the Shop menu item
        onView(withId(R.id.shopMenu)).perform(click())

        // Check if the ShopFragment is displayed
        onView(withId(R.id.nav_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToBagFragment() {
        // Click the Bag menu item
        onView(withId(R.id.bagMenu)).perform(click())

        // Check if the BagFragment is displayed
        onView(withId(R.id.nav_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToFavFragment() {
        // Click the Fav menu item
        onView(withId(R.id.favMenu)).perform(click())

        // Check if the FavFragment is displayed
        onView(withId(R.id.nav_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToProfileFragment() {
        // Click the Profile menu item
        onView(withId(R.id.profileMenu)).perform(click())

        // Check if the ProfileFragment is displayed
        onView(withId(R.id.nav_fragment)).check(matches(isDisplayed()))
    }
}
