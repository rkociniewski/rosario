package rk.powermilk.rosario.enums

import rk.powermilk.rosario.R

enum class PrayerType(override val label: Int) : DisplayText {
    ROSARY(R.string.prayer_type_rosary),
    DIVINE_MERCY(R.string.prayer_type_divine_mercy),
    JESUS_PRAYER(R.string.prayer_type_chotka)
}
