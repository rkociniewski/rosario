package pl.rk.rosario.ui.parts

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import pl.rk.rosario.R
import pl.rk.rosario.enums.BeadType
import pl.rk.rosario.enums.DisplayMode
import pl.rk.rosario.model.Bead
import java.util.Locale

private const val THREE = 3
private const val FOUR = 4
private const val FIVE = 5
private const val SIX = 5
private const val TEN = 10

fun generateRosaryBeads() = buildList {
    add(Bead(0, BeadType.CROSS, R.string.prayer_in_the_name))
    add(Bead(0, BeadType.CROSS, R.string.prayer_apostles_creed))
    add(Bead(1, BeadType.TAIL_LARGE, R.string.prayer_our_father))
    add(Bead(2, BeadType.TAIL_SMALL, R.string.prayer_faith))
    add(Bead(THREE, BeadType.TAIL_SMALL, R.string.prayer_hope))
    add(Bead(FOUR, BeadType.TAIL_SMALL, R.string.prayer_love))
    add(Bead(FOUR, BeadType.TAIL_SMALL, R.string.prayer_love))
    add(Bead(SIX, BeadType.BEAD_LARGE, R.string.prayer_glory_be))
    add(Bead(SIX, BeadType.BEAD_LARGE, R.string.prayer_o_my_jesus))
    add(Bead(SIX, BeadType.BEAD_LARGE, R.string.prayer_our_father))

    var actualIndex = SIX

    repeat(FIVE) {
        repeat(TEN) {
            add(Bead(actualIndex++, BeadType.BEAD_SMALL, R.string.prayer_hail_mary))
        }

        if (it < FIVE - 1) {
            add(Bead(actualIndex, BeadType.BEAD_LARGE, R.string.prayer_glory_be))
            add(Bead(actualIndex, BeadType.BEAD_LARGE, R.string.prayer_o_my_jesus))
            add(Bead(actualIndex++, BeadType.BEAD_LARGE, R.string.prayer_our_father))
        }
    }
    add(Bead(SIX, BeadType.BEAD_LARGE, R.string.prayer_glory_be))
    add(Bead(SIX, BeadType.BEAD_LARGE, R.string.prayer_o_my_jesus))
    add(Bead(SIX, BeadType.BEAD_LARGE, R.string.prayer_our_father))

    add(Bead(0, BeadType.CROSS, R.string.prayer_in_the_name))
}

fun generateDivineMercyBeads() = buildList {
    add(Bead(0, BeadType.CROSS, R.string.prayer_in_the_name))
    add(Bead(1, BeadType.TAIL_LARGE))
    add(Bead(2, BeadType.TAIL_SMALL, R.string.prayer_our_father))
    add(Bead(THREE, BeadType.TAIL_SMALL, R.string.prayer_hail_mary))
    add(Bead(FOUR, BeadType.TAIL_SMALL, R.string.prayer_apostles_creed))

    var actualIndex = FIVE

    repeat(FIVE) {
        add(Bead(actualIndex++, BeadType.BEAD_LARGE, R.string.prayer_ethernal_father))
        repeat(TEN) {
            add(Bead(actualIndex++, BeadType.BEAD_SMALL, R.string.prayer_for_the_sake))
        }
    }

    add(Bead(FOUR, BeadType.TAIL_SMALL, R.string.prayer_holy_god))
    add(Bead(THREE, BeadType.TAIL_SMALL, R.string.prayer_holy_god))
    add(Bead(2, BeadType.TAIL_LARGE, R.string.prayer_holy_god))

    add(Bead(1, BeadType.TAIL_LARGE, R.string.prayer_o_blood_and_water))

    add(Bead(FOUR, BeadType.TAIL_LARGE, R.string.prayer_jesus_I_trust))
    add(Bead(THREE, BeadType.TAIL_LARGE, R.string.prayer_jesus_I_trust))
    add(Bead(2, BeadType.TAIL_LARGE, R.string.prayer_jesus_I_trust))

    add(Bead(0, BeadType.CROSS, R.string.prayer_in_the_name))
}

fun generateChotkaBeads() = buildList {
    add(Bead(0, BeadType.CROSS))
    add(Bead(1, BeadType.TAIL_LARGE))
    add(Bead(2, BeadType.TAIL_SMALL))
    add(Bead(THREE, BeadType.TAIL_SMALL))
    add(Bead(FOUR, BeadType.TAIL_SMALL))

    var actualIndex = FIVE

    repeat(FIVE) {
        add(Bead(actualIndex++, BeadType.BEAD_LARGE))
        repeat(TEN) {
            add(Bead(actualIndex++, BeadType.BEAD_SMALL, R.string.prayer_lord_jesus))
        }
    }
}

@SuppressLint("LocalContextConfigurationRead")
@Composable
fun rememberLocalizedContext(languageCode: String): Context {
    val baseContext = LocalContext.current
    return remember(languageCode) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(baseContext.resources.configuration)
        config.setLocale(locale)

        baseContext.createConfigurationContext(config)
    }
}

@Composable
fun DisplayMode.isDarkTheme() = when (this) {
    DisplayMode.SYSTEM -> isSystemInDarkTheme()
    DisplayMode.DARK -> true
    DisplayMode.LIGHT -> false
}