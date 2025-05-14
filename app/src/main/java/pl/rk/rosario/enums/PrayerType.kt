package pl.rk.rosario.enums

import kotlinx.serialization.Serializable
import pl.rk.rosario.R

@Serializable
enum class PrayerType(override val label: Int) : DisplayText {
    ROSARY(R.string.prayer_type_rosary),
    DIVINE_MERCY(R.string.prayer_type_divine_mercy),
    JESUS_PRAYER(R.string.prayer_type_jesus_prayer)
}
