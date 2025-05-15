package com.siri.myb2cecommerce.ui.home


import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.siri.myb2cecommerce.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @Test
    fun testEmptyEmailShowsError() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.loginBtn)).perform(click())

        onView(withId(R.id.emailError))
            .check(matches(isDisplayed()))
            .check(matches(withText("Email Can't be Empty")))
    }

    @Test
    fun testInvalidEmailShowsError() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.emailEt)).perform(typeText("invalidEmail"), closeSoftKeyboard())
        onView(withId(R.id.loginBtn)).perform(click())

        onView(withId(R.id.emailError))
            .check(matches(isDisplayed()))
            .check(matches(withText("Enter Valid Email")))
    }

    @Test
    fun testEmptyPasswordShowsError() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.emailEt)).perform(typeText("test@example.com"), closeSoftKeyboard())
        onView(withId(R.id.loginBtn)).perform(click())

        onView(withId(R.id.passwordError))
            .check(matches(isDisplayed()))
            .check(matches(withText("Password Can't be Empty")))
    }

    @Test
    fun testValidInputGoesToHomeActivity() {
        val scenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.emailEt)).perform(typeText("test@example.com"), closeSoftKeyboard())
        onView(withId(R.id.PassEt)).perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.loginBtn)).perform(click())

        // ถ้าไม่มี Exception คือผ่านแล้ว
        scenario.onActivity { activity ->
            assert(activity.isFinishing) // เพราะมัน start HomeActivity แล้ว finish ตัวเอง
        }
    }
}
