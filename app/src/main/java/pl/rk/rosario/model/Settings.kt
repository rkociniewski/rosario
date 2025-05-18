package pl.rk.rosario.model

import pl.rk.rosario.enums.DisplayMode
import pl.rk.rosario.enums.Language
import pl.rk.rosario.enums.NavigationMode
import pl.rk.rosario.enums.PrayerLocation
import pl.rk.rosario.enums.PrayerType

data class Settings(
    val language: Language = Language.EN,
    val navigationMode: NavigationMode = NavigationMode.TAP,
    val prayer: PrayerType = PrayerType.ROSARY,
    val displayMode: DisplayMode = DisplayMode.SYSTEM,
    val allowRewind: Boolean = true,
    val prayerLocation: PrayerLocation = PrayerLocation.BOTTOM,
)
