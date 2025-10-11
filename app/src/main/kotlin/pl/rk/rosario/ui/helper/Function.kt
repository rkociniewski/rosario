package pl.rk.rosario.ui.helper

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import pl.rk.rosario.R
import pl.rk.rosario.enums.BeadType
import pl.rk.rosario.enums.DisplayMode
import pl.rk.rosario.enums.PrayerType
import pl.rk.rosario.model.Bead
import pl.rk.rosario.util.Numbers
import java.util.Locale

private fun generateRosaryBeads() = buildList {
    add(Bead(Numbers.ZERO, BeadType.CROSS, R.string.prayer_in_the_name))
    add(Bead(Numbers.ZERO, BeadType.CROSS, R.string.prayer_apostles_creed))
    add(Bead(Numbers.ONE, BeadType.TAIL_LARGE, R.string.prayer_our_father))
    add(Bead(Numbers.TWO, BeadType.TAIL_SMALL, R.string.prayer_faith))
    add(Bead(Numbers.THREE, BeadType.TAIL_SMALL, R.string.prayer_hope))
    add(Bead(Numbers.FOUR, BeadType.TAIL_SMALL, R.string.prayer_love))
    add(Bead(Numbers.FOUR, BeadType.TAIL_SMALL, R.string.prayer_love))
    add(Bead(Numbers.FIVE, BeadType.BEAD_LARGE, R.string.prayer_glory_be))
    add(Bead(Numbers.FIVE, BeadType.BEAD_LARGE, R.string.prayer_o_my_jesus))

    var actualIndex = Numbers.FIVE

    repeat(Numbers.FIVE) {
        add(Bead(actualIndex++, BeadType.BEAD_LARGE, R.string.prayer_our_father))
        repeat(Numbers.TEN) {
            add(Bead(actualIndex++, BeadType.BEAD_SMALL, R.string.prayer_hail_mary))
        }

        if (it < Numbers.FIVE - 1) {
            add(Bead(actualIndex, BeadType.BEAD_LARGE, R.string.prayer_glory_be))
            add(Bead(actualIndex, BeadType.BEAD_LARGE, R.string.prayer_o_my_jesus))
        }
    }
    add(Bead(Numbers.FIVE, BeadType.BEAD_LARGE, R.string.prayer_glory_be))
    add(Bead(Numbers.FIVE, BeadType.BEAD_LARGE, R.string.prayer_o_my_jesus))
    add(Bead(Numbers.FIVE, BeadType.BEAD_LARGE, R.string.prayer_our_father))

    add(Bead(Numbers.ZERO, BeadType.CROSS, R.string.prayer_in_the_name))
}

private fun generateDivineMercyBeads() = buildList {
    add(Bead(Numbers.ZERO, BeadType.CROSS, R.string.prayer_in_the_name))
    add(Bead(Numbers.ONE, BeadType.TAIL_LARGE))
    add(Bead(Numbers.TWO, BeadType.TAIL_SMALL, R.string.prayer_our_father))
    add(Bead(Numbers.THREE, BeadType.TAIL_SMALL, R.string.prayer_hail_mary))
    add(Bead(Numbers.FOUR, BeadType.TAIL_SMALL, R.string.prayer_apostles_creed))

    var actualIndex = Numbers.FIVE

    repeat(Numbers.FIVE) {
        add(Bead(actualIndex++, BeadType.BEAD_LARGE, R.string.prayer_ethernal_father))
        repeat(Numbers.TEN) {
            add(Bead(actualIndex++, BeadType.BEAD_SMALL, R.string.prayer_for_the_sake))
        }
    }

    add(Bead(Numbers.FOUR, BeadType.TAIL_SMALL, R.string.prayer_holy_god))
    add(Bead(Numbers.THREE, BeadType.TAIL_SMALL, R.string.prayer_holy_god))
    add(Bead(Numbers.TWO, BeadType.TAIL_SMALL, R.string.prayer_holy_god))

    add(Bead(Numbers.ONE, BeadType.TAIL_LARGE, R.string.prayer_o_blood_and_water))

    add(Bead(Numbers.FOUR, BeadType.TAIL_SMALL, R.string.prayer_jesus_I_trust))
    add(Bead(Numbers.THREE, BeadType.TAIL_SMALL, R.string.prayer_jesus_I_trust))
    add(Bead(Numbers.TWO, BeadType.TAIL_SMALL, R.string.prayer_jesus_I_trust))

    add(Bead(Numbers.ZERO, BeadType.CROSS, R.string.prayer_in_the_name))
}

private fun generateChotkaBeads() = buildList {
    add(Bead(Numbers.ZERO, BeadType.CROSS))
    add(Bead(Numbers.ONE, BeadType.TAIL_LARGE))
    add(Bead(Numbers.TWO, BeadType.TAIL_SMALL))
    add(Bead(Numbers.THREE, BeadType.TAIL_SMALL))
    add(Bead(Numbers.FOUR, BeadType.TAIL_SMALL))

    var actualIndex = Numbers.FIVE

    repeat(Numbers.FIVE) {
        add(Bead(actualIndex++, BeadType.BEAD_LARGE))
        repeat(Numbers.TEN) {
            add(Bead(actualIndex++, BeadType.BEAD_SMALL, R.string.prayer_lord_jesus))
        }
    }
}

@Composable
fun rememberLocalizedContext(languageCode: String): Context {
    val baseContext = LocalContext.current
    val localConfiguration = LocalConfiguration.current
    return remember(languageCode) {
        val locale = Locale.Builder()
            .setLanguage(languageCode)
            .build()

        Locale.setDefault(locale)

        val config = Configuration(localConfiguration)
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

fun PrayerType.generateBeads() = when (this) {
    PrayerType.ROSARY -> generateRosaryBeads()
    PrayerType.DIVINE_MERCY -> generateDivineMercyBeads()
    PrayerType.JESUS_PRAYER -> generateChotkaBeads()
}
