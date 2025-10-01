package pl.rk.rosario.enums

import androidx.annotation.StringRes
import pl.rk.rosario.R

/**
 *
 * @property label Resource ID for the display label of this mode
 */
enum class NavigationMode(@param:StringRes override val label: Int) : DisplayText {
    BUTTON(R.string.navigation_mode_button),
    TAP(R.string.navigation_mode_tap),
    BOTH(R.string.navigation_mode_both)
}
