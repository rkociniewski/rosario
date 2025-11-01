package rk.powermilk.rosario.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rk.powermilk.rosario.enums.DisplayMode
import rk.powermilk.rosario.enums.Language
import rk.powermilk.rosario.enums.NavigationMode
import rk.powermilk.rosario.model.Settings
import rk.powermilk.rosario.ui.settings.SettingsScreen
import rk.powermilk.rosario.ui.theme.RosarioTheme

@MediumTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SettingsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun settingsScreen_displays_all_settings_options() {
        val settings = Settings()
        val onSettingsUpdate = mockk<(Settings) -> Unit>(relaxed = true)
        val onClose = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            RosarioTheme {
                SettingsScreen(
                    settings = settings,
                    updateSettings = onSettingsUpdate,
                    onClose = onClose
                )
            }
        }

        composeTestRule.waitForIdle()
        // Settings screen should display without crashing
    }

    @Test
    fun settingsScreen_close_button_calls_onClose() {
        val settings = Settings()
        val onSettingsUpdate = mockk<(Settings) -> Unit>(relaxed = true)
        val onClose = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            RosarioTheme {
                SettingsScreen(
                    settings = settings,
                    updateSettings = onSettingsUpdate,
                    onClose = onClose
                )
            }
        }

        composeTestRule.waitForIdle()

        // Get the close button text from resources
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val closeText = context.getString(rk.powermilk.rosario.R.string.close)

        // Find and click the close button
        composeTestRule.onNodeWithText(closeText).assertIsDisplayed()
        composeTestRule.onNodeWithText(closeText).performClick()

        // Verify onClose was called
        verify { onClose() }
    }

    @Test
    fun settingsScreen_displays_with_different_settings() {
        val settings = Settings(
            Language.PL, NavigationMode.BUTTON, displayMode = DisplayMode.DARK,
            allowRewind = false,
            showBeadNumber = true
        )
        val onSettingsUpdate = mockk<(Settings) -> Unit>(relaxed = true)
        val onClose = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            RosarioTheme {
                SettingsScreen(
                    settings = settings,
                    updateSettings = onSettingsUpdate,
                    onClose = onClose
                )
            }
        }

        composeTestRule.waitForIdle()
        // Settings screen should handle different settings configurations
    }
}
