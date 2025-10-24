package rk.powermilk.rosario.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import rk.powermilk.rosario.R

/**
 * @property label Resource ID for the display label of this mode
 */
enum class Language(@param:StringRes override val label: Int, @param:DrawableRes val flagIcon: Int) :
    DisplayText {
    PL(R.string.lang_pl, R.drawable.flag_pl),
    EN(R.string.lang_en, R.drawable.flag_uk),
    FR(R.string.lang_fr, R.drawable.flag_fr),
    IT(R.string.lang_it, R.drawable.flag_it),
    ES(R.string.lang_es, R.drawable.flag_es),
}
