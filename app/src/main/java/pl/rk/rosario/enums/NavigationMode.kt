package pl.rk.rosario.enums

import androidx.annotation.StringRes
import kotlinx.serialization.Serializable
import pl.rk.rosario.R

/**
 *
 * @property label Resource ID for the display label of this mode
 */
@Serializable
enum class NavigationMode(@StringRes override val label: Int) : DisplayText {
    BUTTON(R.string.navigation_mode_button),
    TAP(R.string.navigation_mode_tap),
    BOTH(R.string.navigation_mode_both)
}
