package pl.rk.rosario.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.rk.rosario.R
import pl.rk.rosario.enums.DisplayMode
import pl.rk.rosario.enums.Language
import pl.rk.rosario.enums.NavigationMode
import pl.rk.rosario.ui.parts.EnumSelector
import pl.rk.rosario.ui.parts.HelpLabel
import pl.rk.rosario.ui.parts.localRosaryViewModel

@Composable
fun SettingsScreen() {
    val viewModel = localRosaryViewModel.current
    val settings by viewModel.settings.collectAsState()

    Column(Modifier.padding(16.dp)) {
        Text(
            stringResource(R.string.settings_label_lang),
            style = MaterialTheme.typography.titleMedium
        )
        EnumSelector(Language.entries, settings.language) {
            viewModel.updateSettings(settings.copy(language = it))
        }

        HorizontalDivider()

        Text(
            stringResource(R.string.settings_label_mode_change),
            style = MaterialTheme.typography.titleMedium
        )
        EnumSelector(NavigationMode.entries, settings.navigationMode) {
            viewModel.updateSettings(settings.copy(navigationMode = it))
        }

        HorizontalDivider()

        HelpLabel(
            stringResource(R.string.settings_label_display_mode),
            stringResource(R.string.tooltip_display_mode)
        )
        EnumSelector(DisplayMode.entries, settings.displayMode) {
            viewModel.updateSettings(settings.copy(displayMode = it))
        }

        HorizontalDivider()

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = settings.allowRewind,
                onCheckedChange = { viewModel.updateSettings(settings.copy(allowRewind = it)) }
            )
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.settings_allow_rewind))
        }
    }
}