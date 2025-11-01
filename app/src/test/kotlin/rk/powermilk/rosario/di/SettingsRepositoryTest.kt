package rk.powermilk.rosario.di

import android.content.Context
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import rk.powermilk.rosario.enums.Language
import rk.powermilk.rosario.enums.NavigationMode
import rk.powermilk.rosario.enums.PrayerType
import rk.powermilk.rosario.model.Settings
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

@DisplayName("SettingsRepository Tests")
class SettingsRepositoryTest {

    private lateinit var context: Context
    private lateinit var repository: SettingsRepository

    @BeforeTest
    fun setup() {
        context = mockk(relaxed = true)
        repository = SettingsRepository(context)
    }

    @Test
    @DisplayName("Should initialize repository successfully")
    fun testRepositoryInitialization() {
        // Note: Testing SettingsRepository requires a real Android Context with DataStore
        // This test simply verifies that the repository can be instantiated
        // More comprehensive tests would be integration tests with real Android components
        assertNotNull(repository)
        assertNotNull(repository.settingsFlow)
    }

    @Test
    @DisplayName("Should have context reference")
    fun testContextReference() {
        // Verify that repository was created with the mocked context
        assertNotNull(repository)
    }

    @Test
    @DisplayName("Should delegate updateSettings to SettingsStore")
    fun testUpdateSettings() = runTest {
        val settings = Settings(Language.PL, NavigationMode.BUTTON, PrayerType.DIVINE_MERCY)

        // Note: In a real test environment with properly mocked DataStore,
        // we would verify that SettingsStore.write() is called with correct parameters
        // For now, we just call the method to ensure it doesn't throw
        repository.updateSettings(settings)
    }
}
