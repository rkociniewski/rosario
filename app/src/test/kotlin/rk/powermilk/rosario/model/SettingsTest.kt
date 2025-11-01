package rk.powermilk.rosario.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import rk.powermilk.rosario.enums.DisplayMode
import rk.powermilk.rosario.enums.Language
import rk.powermilk.rosario.enums.NavigationMode
import rk.powermilk.rosario.enums.PrayerLocation
import rk.powermilk.rosario.enums.PrayerType
import java.util.Locale

@DisplayName("Settings Model Tests")
class SettingsTest {

    @Nested
    @DisplayName("Default Settings Tests")
    inner class DefaultSettingsTests {

        @Test
        @DisplayName("Should create settings with default values")
        fun testDefaultSettings() {
            val settings = Settings()

            assertNotNull(settings)
            assertEquals(NavigationMode.TAP, settings.navigationMode)
            assertEquals(PrayerType.ROSARY, settings.prayer)
            assertEquals(DisplayMode.SYSTEM, settings.displayMode)
            assertTrue(settings.allowRewind)
            assertEquals(PrayerLocation.BOTTOM, settings.prayerLocation)
            assertFalse(settings.showBeadNumber)
        }

        @Test
        @DisplayName("Should resolve default language from system locale")
        fun testDefaultLanguageResolution() {
            val defaultLanguage = resolveDefaultLanguage()

            assertNotNull(defaultLanguage)
            assertTrue(Language.entries.contains(defaultLanguage))
        }
    }

    @Nested
    @DisplayName("Custom Settings Tests")
    inner class CustomSettingsTests {

        @Test
        @DisplayName("Should create settings with custom values")
        fun testCustomSettings() {
            val settings = Settings(
                Language.PL,
                NavigationMode.BUTTON,
                PrayerType.DIVINE_MERCY,
                DisplayMode.DARK,
                false,
                PrayerLocation.TOP,
                true
            )

            assertEquals(Language.PL, settings.language)
            assertEquals(NavigationMode.BUTTON, settings.navigationMode)
            assertEquals(PrayerType.DIVINE_MERCY, settings.prayer)
            assertEquals(DisplayMode.DARK, settings.displayMode)
            assertFalse(settings.allowRewind)
            assertEquals(PrayerLocation.TOP, settings.prayerLocation)
            assertTrue(settings.showBeadNumber)
        }

        @ParameterizedTest
        @EnumSource(Language::class)
        @DisplayName("Should support all language options")
        fun testAllLanguages(language: Language) {
            val settings = Settings(language = language)

            assertEquals(language, settings.language)
        }

        @ParameterizedTest
        @EnumSource(NavigationMode::class)
        @DisplayName("Should support all navigation modes")
        fun testAllNavigationModes(mode: NavigationMode) {
            val settings = Settings(navigationMode = mode)

            assertEquals(mode, settings.navigationMode)
        }

        @ParameterizedTest
        @EnumSource(PrayerType::class)
        @DisplayName("Should support all prayer types")
        fun testAllPrayerTypes(prayerType: PrayerType) {
            val settings = Settings(prayer = prayerType)

            assertEquals(prayerType, settings.prayer)
        }

        @ParameterizedTest
        @EnumSource(DisplayMode::class)
        @DisplayName("Should support all display modes")
        fun testAllDisplayModes(displayMode: DisplayMode) {
            val settings = Settings(displayMode = displayMode)

            assertEquals(displayMode, settings.displayMode)
        }

        @ParameterizedTest
        @EnumSource(PrayerLocation::class)
        @DisplayName("Should support all prayer locations")
        fun testAllPrayerLocations(location: PrayerLocation) {
            val settings = Settings(prayerLocation = location)

            assertEquals(location, settings.prayerLocation)
        }
    }

    @Nested
    @DisplayName("Settings Copy Tests")
    inner class SettingsCopyTests {

        @Test
        @DisplayName("Should create copy with modified language")
        fun testCopyWithLanguage() {
            val original = Settings()
            val modified = original.copy(language = Language.FR)

            assertEquals(Language.FR, modified.language)
            assertEquals(original.navigationMode, modified.navigationMode)
            assertEquals(original.prayer, modified.prayer)
        }

        @Test
        @DisplayName("Should create copy with modified navigation mode")
        fun testCopyWithNavigationMode() {
            val original = Settings()
            val modified = original.copy(navigationMode = NavigationMode.BOTH)

            assertEquals(NavigationMode.BOTH, modified.navigationMode)
            assertEquals(original.language, modified.language)
        }

        @Test
        @DisplayName("Should create copy with modified prayer type")
        fun testCopyWithPrayerType() {
            val original = Settings()
            val modified = original.copy(prayer = PrayerType.JESUS_PRAYER)

            assertEquals(PrayerType.JESUS_PRAYER, modified.prayer)
            assertEquals(original.language, modified.language)
        }
    }

    @Nested
    @DisplayName("Language Resolution Tests")
    inner class LanguageResolutionTests {

        @Test
        @DisplayName("Should fallback to EN for unsupported locale")
        fun testUnsupportedLocale() {
            val currentLocale = Locale.getDefault()
            try {
                // Set to a locale that's not in the Language enum
                Locale.setDefault(Locale.SIMPLIFIED_CHINESE)

                val language = resolveDefaultLanguage()

                assertEquals(Language.EN, language)
            } finally {
                Locale.setDefault(currentLocale)
            }
        }

        @Test
        @DisplayName("Should resolve supported language from locale")
        fun testSupportedLocale() {
            val currentLocale = Locale.getDefault()
            try {
                Locale.setDefault(Locale.forLanguageTag("pl"))

                val language = resolveDefaultLanguage()

                assertEquals(Language.PL, language)
            } finally {
                Locale.setDefault(currentLocale)
            }
        }
    }
}
