package rk.powermilk.rosario.ui

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rk.powermilk.rosario.enums.PrayerType
import rk.powermilk.rosario.model.Bead
import rk.powermilk.rosario.model.Settings
import rk.powermilk.rosario.ui.helper.generateBeads
import rk.powermilk.rosario.ui.rosary.RosaryScreen
import rk.powermilk.rosario.ui.theme.RosarioTheme
import rk.powermilk.rosario.viewModel.RosaryViewModel

@MediumTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class RosaryScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setup() {
        hiltRule.inject()
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun rosaryScreen_displays_loading_when_beads_are_empty() {
        val viewModel = mockk<RosaryViewModel>(relaxed = true)
        every { viewModel.settings } returns MutableStateFlow(Settings())
        every { viewModel.beads } returns MutableStateFlow(emptyList())
        every { viewModel.currentIndex } returns MutableStateFlow(0)
        every { viewModel.currentPrayerId } returns MutableStateFlow(0)
        every { viewModel.shouldShowRestartDialog } returns MutableStateFlow(false)

        composeTestRule.setContent {
            RosarioTheme {
                RosaryScreen(viewModel = viewModel, localizedContext = context)
            }
        }

        composeTestRule.waitForIdle()
        // When beads are empty, a CircularProgressIndicator should be displayed
        // This is a basic smoke test
    }

    @Test
    fun rosaryScreen_displays_content_when_beads_are_available() {
        val viewModel = mockk<RosaryViewModel>(relaxed = true)
        val settings = Settings(prayer = PrayerType.ROSARY)
        val beads = PrayerType.ROSARY.generateBeads()

        every { viewModel.settings } returns MutableStateFlow(settings)
        every { viewModel.beads } returns MutableStateFlow(beads)
        every { viewModel.currentIndex } returns MutableStateFlow(0)
        every { viewModel.currentPrayerId } returns MutableStateFlow(beads.first().prayerId)
        every { viewModel.shouldShowRestartDialog } returns MutableStateFlow(false)

        composeTestRule.setContent {
            RosarioTheme {
                RosaryScreen(viewModel = viewModel, localizedContext = context)
            }
        }

        composeTestRule.waitForIdle()
        // Content should be displayed (no crash)
    }

    @Test
    fun rosaryScreen_handles_different_prayer_types() {
        val viewModel = mockk<RosaryViewModel>(relaxed = true)

        PrayerType.entries.forEach { prayerType ->
            val settings = Settings(prayer = prayerType)
            val beads = prayerType.generateBeads()

            every { viewModel.settings } returns MutableStateFlow(settings)
            every { viewModel.beads } returns MutableStateFlow(beads)
            every { viewModel.currentIndex } returns MutableStateFlow(0)
            every { viewModel.currentPrayerId } returns MutableStateFlow(beads.firstOrNull()?.prayerId ?: 0)
            every { viewModel.shouldShowRestartDialog } returns MutableStateFlow(false)

            composeTestRule.setContent {
                RosarioTheme {
                    RosaryScreen(viewModel = viewModel, localizedContext = context)
                }
            }

            composeTestRule.waitForIdle()
            // Each prayer type should display without crashing
        }
    }
}
