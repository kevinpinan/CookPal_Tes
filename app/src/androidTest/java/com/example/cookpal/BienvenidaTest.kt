package com.example.cookpal


import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class BienvenidaTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(Bienvenida::class.java)

    @Test
    fun bienvenidaTest() {
    }
}
