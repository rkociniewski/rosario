package pl.rk.rosario.enums

import pl.rk.rosario.R

enum class PrayerLocation(override val label: Int) : DisplayText {
    TOP(R.string.location_top),
    BOTTOM(R.string.location_botom),
}
