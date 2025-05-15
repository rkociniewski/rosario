package pl.rk.rosario.enums

import androidx.annotation.StringRes
import pl.rk.rosario.R

/**
 * @property label Resource ID for the display label of this mode
 */
enum class Language(@StringRes override val label: Int) : DisplayText {
    PL(R.string.lang_pl),
    EN(R.string.lang_en),
    FR(R.string.lang_fr),
    IT(R.string.lang_it),
    ES(R.string.lang_es),
}
