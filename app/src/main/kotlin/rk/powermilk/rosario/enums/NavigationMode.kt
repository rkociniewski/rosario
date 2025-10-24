package rk.powermilk.rosario.enums

import androidx.annotation.StringRes
import rk.powermilk.rosario.R

/**
 *
 * @property label Resource ID for the display label of this mode
 */
enum class NavigationMode(@param:StringRes override val label: Int) : DisplayText {
    BUTTON(R.string.navigation_mode_button),
    TAP(R.string.navigation_mode_tap),
    BOTH(R.string.navigation_mode_both)
}
