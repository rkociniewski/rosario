package pl.rk.rosario.model

import kotlinx.serialization.Serializable
import pl.rk.rosario.enums.DisplayMode
import pl.rk.rosario.enums.Language
import pl.rk.rosario.enums.NavigationMode
import pl.rk.rosario.enums.PrayerType

@Serializable
data class Settings(
    val language: Language = Language.EN,
    val navigationMode: NavigationMode = NavigationMode.TAP,
    val prayer: PrayerType = PrayerType.ROSARY,
    val displayMode: DisplayMode = DisplayMode.SYSTEM,
    val allowRewind: Boolean = false
)
