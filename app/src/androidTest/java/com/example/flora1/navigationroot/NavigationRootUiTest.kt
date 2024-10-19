package com.example.flora1.navigationroot

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isNotFocused
import androidx.compose.ui.test.isNotSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.example.flora1.MainActivity
import com.example.flora1.R
import com.example.flora1.android_test.hasTestTag
import com.example.flora1.android_test.onAllUnmergedNodes
import com.example.flora1.android_test.onUnmergedNode
import com.example.flora1.data.preferences.shouldShowOnBoarding
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkObject
import io.mockk.unmockkStatic
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationRootUiTest {
    private var context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            mockkStatic(Context::shouldShowOnBoarding)
            mockkObject(Screen)
            every { Screen.startDestination} returns Screen.Splash.name
        }

        @AfterClass
        @JvmStatic
        fun afterClass() {
            unmockkStatic(Context::shouldShowOnBoarding)
            unmockkObject(Screen)
        }
    }

    @Before
    fun init() {
    }

    @Test
    fun when_should_showOnBoarding_completes_successfully() {
        // given
        every { any<Context>().shouldShowOnBoarding } returns true

        // Username
        composeRule.onNode(hasSetTextAction()).performTextInput("Christinaaa")
        composeRule.onNode(hasTestTag(R.string.primary_button_test_tag)).performClick()

        // Born
        composeRule.onNode(hasContentDescription("Switch to text input mode")).performClick()
        composeRule.onNode(hasSetTextAction()).performTextInput("10102000")
        composeRule.onNode(hasTestTag(R.string.primary_button_test_tag)).performClick()

        // Height
        composeRule.onNode(hasTestTag(R.string.primary_button_test_tag)).performClick()

        // Weight
        composeRule.onNode(hasTestTag(R.string.primary_button_test_tag)).performClick()

        // Pregnancy
        composeRule.onNode(hasText("Yes")).performClick()
        composeRule.onNode(hasTestTag(R.string.primary_button_test_tag)).performClick()

        // Pregnancy Stats
        composeRule.onUnmergedNode(hasTestTag(R.string.dropdown_pregnancy_test_tag)).performClick()
        composeRule.onUnmergedNode(hasText("1")).performClick()
        composeRule.onUnmergedNode(hasTestTag(
            R.string.dropdown_miscarriages_test_tag))
            .performClick()
        composeRule.onUnmergedNode(hasText("2")).performClick()
        composeRule.onUnmergedNode(hasTestTag(R.string.dropdown_abortions_test_tag)).performClick()
        composeRule.onUnmergedNode(hasText("3")).performClick()
        composeRule.waitForIdle()
        composeRule.onNode(hasTestTag(R.string.primary_button_test_tag)).performClick()

        // Race
        composeRule.onUnmergedNode(hasTestTag(R.string.dropdown_race_test_tag)).performClick()
        composeRule.onUnmergedNode(hasText("White")).performClick()
        composeRule.onNode(hasTestTag(R.string.primary_button_test_tag)).performClick()

        // MedVits
        composeRule.onNode(hasText("Yes")).performClick()
        composeRule.onNode(hasSetTextAction()).performTextInput("Yes, I have")
        composeRule.onNode(hasTestTag(R.string.icon_next_test_tag)).performClick()

        // Gynecosurgery
        composeRule.onNode(hasText("Yes")).performClick()
        composeRule.onNode(hasSetTextAction()).performTextInput("Yes, I have")
        composeRule.onNode(hasTestTag(R.string.icon_next_test_tag)).performClick()

        // Contraceptives
        composeRule.onNode(hasTestTag(R.string.clickable_text_field_test_tag)).performClick()
        composeRule.onNode(hasText("Pill")).performClick()
        composeRule.onNode(hasTestTag(R.string.close_icon_test_tag)).performClick()
        composeRule.onNode(hasTestTag(R.string.icon_next_test_tag)).performClick()

        // Stress Levels
        composeRule.onNode(hasText("Low")).performClick()
        composeRule.onNode(hasTestTag(R.string.icon_next_test_tag)).performClick()

        // Sleep Quality
        composeRule.onNode(hasText("Unsatisfactory")).performClick()
        composeRule.onNode(hasTestTag(R.string.icon_next_test_tag)).performClick()

        // Average Cycle
        composeRule.onNode(hasText("30")).performClick()
        composeRule.onNode(hasTestTag(R.string.icon_next_test_tag)).performClick()

        // Last Period

        /*
        Clicks 1st unselected and enabled date
         */
        val selectableDates = composeRule.onAllUnmergedNodes(hasClickAction() and isNotSelected() and isNotFocused())

        if(selectableDates.fetchSemanticsNodes().isNotEmpty())
            selectableDates[0].performClick()

        composeRule.onNode(hasTestTag(R.string.icon_next_test_tag)).performClick()

        // Get Started!
        composeRule.onNode(hasText("Get Started!")).performClick()
    }

    @Test
    fun when_should_showOnBoarding_navigates_to_main_screen() {
        // given
        every { any<Context>().shouldShowOnBoarding } returns false

        // then
        composeRule.onNode(hasTestTag(R.string.prediction_dialog_test_tag)).assertIsDisplayed()

    }
}
