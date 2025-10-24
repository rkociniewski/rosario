package rk.powermilk.rosario.model

import rk.powermilk.rosario.enums.DisplayMode
import rk.powermilk.rosario.enums.Language
import rk.powermilk.rosario.enums.NavigationMode
import rk.powermilk.rosario.enums.PrayerLocation
import rk.powermilk.rosario.enums.PrayerType
import java.util.Locale

data class Settings(
    val language: Language = resolveDefaultLanguage(),
    val navigationMode: NavigationMode = NavigationMode.TAP,
    val prayer: PrayerType = PrayerType.ROSARY,
    val displayMode: DisplayMode = DisplayMode.SYSTEM,
    val allowRewind: Boolean = true,
    val prayerLocation: PrayerLocation = PrayerLocation.BOTTOM,
    val showBeadNumber: Boolean = false,
)

fun resolveDefaultLanguage(): Language {
    val systemLangCode = Locale.getDefault().language.uppercase()
    return Language.entries.firstOrNull { it.name == systemLangCode } ?: Language.EN
}
