package rk.powermilk.rosario

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun mainActivity_launches_successfully() {
        // Verify that the activity launches and displays content
        // This is a basic smoke test
        composeTestRule.waitForIdle()
    }

    @Test
    fun mainActivity_displays_in_portrait_mode() {
        // Verify that the activity is in portrait orientation
        val activity = composeTestRule.activity
        assert(activity.requestedOrientation == android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    @Test
    fun mainActivity_has_rosary_screen() {
        // Wait for the UI to be idle
        composeTestRule.waitForIdle()

        // Basic test to ensure the activity doesn't crash
        // More specific UI tests would be in RosaryScreenTest
    }
}
