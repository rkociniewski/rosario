package rk.powermilk.rosario.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import rk.powermilk.rosario.R
import rk.powermilk.rosario.di.SettingsRepository
import rk.powermilk.rosario.enums.Language
import rk.powermilk.rosario.enums.NavigationMode
import rk.powermilk.rosario.enums.PrayerType
import rk.powermilk.rosario.model.Settings

@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("RosaryViewModel Tests")
class RosaryViewModelTest {

    @get:org.junit.Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var viewModel: RosaryViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        settingsRepository = mockk(relaxed = true)
        every { settingsRepository.settingsFlow } returns flowOf(Settings())
        viewModel = RosaryViewModel(settingsRepository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Nested
    @DisplayName("Initialization Tests")
    inner class InitializationTests {

        @Test
        @DisplayName("Should initialize with default settings")
        fun testInitialState() = runTest {
            viewModel.settings.test {
                val settings = awaitItem()
                assertEquals(NavigationMode.TAP, settings.navigationMode)
                assertEquals(PrayerType.ROSARY, settings.prayer)
                assertTrue(settings.allowRewind)
            }
        }

        @Test
        @DisplayName("Should initialize beads from settings")
        fun testBeadsInitialization() = runTest {
            viewModel.beads.test {
                val beads = awaitItem()
                assertTrue(beads.isNotEmpty())
                // Rosary should have multiple beads
                assertTrue(beads.size > 50)
            }
        }

        @Test
        @DisplayName("Should set initial index to first bead with prayer")
        fun testInitialIndex() = runTest {
            viewModel.currentIndex.test {
                val index = awaitItem()
                val beads = viewModel.beads.value
                // First bead with prayerId != 0 should be at index 0 (cross)
                assertTrue(beads[index].prayerId != 0)
            }
        }
    }

    @Nested
    @DisplayName("Navigation Tests")
    inner class NavigationTests {

        @Test
        @DisplayName("Should move to next bead")
        fun testNext() = runTest {
            val initialIndex = viewModel.currentIndex.value
            viewModel.next()

            viewModel.currentIndex.test {
                val newIndex = awaitItem()
                assertTrue(newIndex > initialIndex)
            }
        }

        @Test
        @DisplayName("Should move to previous bead")
        fun testPrevious() = runTest {
            // Move forward first
            viewModel.next()
            viewModel.next()

            val indexBeforePrevious = viewModel.currentIndex.value
            viewModel.previous()

            viewModel.currentIndex.test {
                val newIndex = awaitItem()
                assertTrue(newIndex < indexBeforePrevious)
            }
        }

        @Test
        @DisplayName("Should skip beads with prayerId 0 when moving next")
        fun testNextSkipsEmptyBeads() = runTest {
            viewModel.next()

            viewModel.currentIndex.test {
                val index = awaitItem()
                val beads = viewModel.beads.value
                assertTrue(beads[index].prayerId != 0)
            }
        }

        @Test
        @DisplayName("Should show restart dialog when reaching end")
        fun testShowRestartDialogAtEnd() = runTest {
            // Move to the end
            repeat(100) {
                if (!viewModel.shouldShowRestartDialog.value) {
                    viewModel.next()
                }
            }

            viewModel.shouldShowRestartDialog.test {
                assertTrue(awaitItem())
            }
        }

        @Test
        @DisplayName("Should not move previous from first bead")
        fun testPreviousAtStart() = runTest {
            val initialIndex = viewModel.currentIndex.value
            viewModel.previous()

            viewModel.currentIndex.test {
                val newIndex = awaitItem()
                assertEquals(initialIndex, newIndex)
            }
        }
    }

    @Nested
    @DisplayName("Reset Tests")
    inner class ResetTests {

        @Test
        @DisplayName("Should reset to first bead when confirmed")
        fun testResetConfirmed() = runTest {
            // Move forward
            repeat(5) { viewModel.next() }

            viewModel.reset(confirmed = true)

            viewModel.currentIndex.test {
                val index = awaitItem()
                val beads = viewModel.beads.value
                assertEquals(beads.first { it.prayerId != 0 }.index, index)
            }
        }

        @Test
        @DisplayName("Should close restart dialog when reset is confirmed")
        fun testResetClosesDialog() = runTest {
            viewModel.reset(confirmed = true)

            viewModel.shouldShowRestartDialog.test {
                assertFalse(awaitItem())
            }
        }

        @Test
        @DisplayName("Should close restart dialog when reset is cancelled")
        fun testResetCancelClosesDialog() = runTest {
            // Move to end to trigger dialog
            repeat(100) {
                if (!viewModel.shouldShowRestartDialog.value) {
                    viewModel.next()
                }
            }

            viewModel.reset(confirmed = false)

            viewModel.shouldShowRestartDialog.test {
                assertFalse(awaitItem())
            }
        }
    }

    @Nested
    @DisplayName("Settings Update Tests")
    inner class SettingsUpdateTests {

        @Test
        @DisplayName("Should update settings successfully")
        fun testUpdateSettings() = runTest {
            val newSettings = Settings(Language.PL, NavigationMode.BUTTON, PrayerType.DIVINE_MERCY)

            coEvery { settingsRepository.updateSettings(any()) } returns true

            viewModel.updateSettings(newSettings)

            coVerify { settingsRepository.updateSettings(newSettings) }
        }

        @Test
        @DisplayName("Should update beads when prayer type changes")
        fun testUpdateBeadsOnPrayerChange() = runTest {
            val newSettings = Settings(prayer = PrayerType.DIVINE_MERCY)

            coEvery { settingsRepository.updateSettings(any()) } returns true
            every { settingsRepository.settingsFlow } returns flowOf(newSettings)

            val newViewModel = RosaryViewModel(settingsRepository)

            newViewModel.beads.test {
                val beads = awaitItem()
                // Divine Mercy has fewer beads than Rosary
                assertTrue(beads.isNotEmpty())
            }
        }

        @Test
        @DisplayName("Should handle settings update failure")
        fun testUpdateSettingsFailure() = runTest {
            val newSettings = Settings(language = Language.FR)

            coEvery { settingsRepository.updateSettings(any()) } returns false

            viewModel.updateSettings(newSettings)

            coVerify { settingsRepository.updateSettings(newSettings) }
        }
    }

    @Nested
    @DisplayName("Current Prayer ID Tests")
    inner class CurrentPrayerIdTests {

        @Test
        @DisplayName("Should update current prayer ID when navigating")
        fun testCurrentPrayerIdUpdate() = runTest {
            viewModel.next()

            viewModel.currentPrayerId.test {
                val prayerId = awaitItem()
                assertTrue(prayerId != 0)
            }
        }

        @Test
        @DisplayName("Should have valid prayer ID at start")
        fun testInitialPrayerId() = runTest {
            viewModel.currentPrayerId.test {
                val prayerId = awaitItem()
                assertEquals(R.string.prayer_in_the_name, prayerId)
            }
        }
    }
}
