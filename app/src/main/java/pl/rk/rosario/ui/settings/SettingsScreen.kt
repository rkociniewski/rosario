package pl.rk.rosario.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pl.rk.rosario.R
import pl.rk.rosario.enums.DisplayMode
import pl.rk.rosario.enums.Language
import pl.rk.rosario.enums.NavigationMode
import pl.rk.rosario.enums.PrayerLocation
import pl.rk.rosario.model.Settings
import pl.rk.rosario.ui.helper.BooleanSelector
import pl.rk.rosario.ui.helper.EnumSelector
import pl.rk.rosario.ui.helper.HelpLabel
import pl.rk.rosario.ui.helper.LanguageSelector
import pl.rk.rosario.util.Dimensions

/**
 * A composable function that displays the configuration section in a modal dialog.
 *
 * This component provides UI controls for all application settings including additional mode options,
 * display mode preferences, and draw mode settings.
 *
 * @param settings The current configuration being edited
 * @param updateSettings Callback to update the configuration when changes are made
 * @param onClose Callback to close the configuration dialog
 */
@Composable
fun SettingsScreen(
    settings: Settings,
    updateSettings: (Settings) -> Unit,
    onClose: () -> Unit,
) {
    Column(
        Modifier.padding(Dimensions.dialogPadding),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        HelpLabel(
            stringResource(R.string.settings_label_language),
            stringResource(R.string.tooltip_language)

        )
        LanguageSelector(Language.entries, settings.language) {
            updateSettings(settings.copy(language = it))
        }

        HorizontalDivider()

        HelpLabel(
            stringResource(R.string.settings_label_navigation_change),
            stringResource(R.string.tooltip_label_navigation_change),
        )
        EnumSelector(NavigationMode.entries, settings.navigationMode) {
            updateSettings(settings.copy(navigationMode = it))
        }

        HorizontalDivider()

        HelpLabel(
            stringResource(R.string.settings_label_display_mode),
            stringResource(R.string.tooltip_display_mode)
        )
        EnumSelector(DisplayMode.entries, settings.displayMode) {
            updateSettings(settings.copy(displayMode = it))
        }

        HorizontalDivider()

        BooleanSelector(settings.allowRewind, stringResource(R.string.settings_allow_rewind)) {
            updateSettings(settings.copy(allowRewind = it))
        }

        HorizontalDivider()

        HelpLabel(
            stringResource(R.string.settings_label_prayer_location),
            stringResource(R.string.tooltip_prayer_location)
        )
        EnumSelector(PrayerLocation.entries, settings.prayerLocation) {
            updateSettings(settings.copy(prayerLocation = it))
        }

        Spacer(Modifier.height(Dimensions.height))

        Button(
            onClose,
            Modifier
                .fillMaxWidth()
                .padding(top = Dimensions.height)
        ) {
            Text(stringResource(R.string.close))
        }
    }
}
