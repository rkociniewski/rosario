package pl.rk.rosario.ui.prayer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.rk.rosario.R
import pl.rk.rosario.enums.PrayerType
import pl.rk.rosario.ui.parts.EnumSelector
import pl.rk.rosario.ui.parts.HelpLabel
import pl.rk.rosario.ui.parts.settingsFlow
import pl.rk.rosario.ui.parts.updateSettings
import pl.rk.rosario.viewModel.PrayerTypeViewModel

@Composable
fun PrayerTypeScreen(viewModel: PrayerTypeViewModel = viewModel(factory = PrayerTypeViewModel.Factory)) {
    val settings by settingsFlow.collectAsState()

    Column(Modifier.padding(16.dp)) {
        HelpLabel(
            stringResource(R.string.settings_label_prayer_type),
            stringResource(R.string.tooltip_label_prayer_type)
        )
        Text(
            stringResource(R.string.settings_label_prayer_type),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))

        EnumSelector(
            options = PrayerType.entries,
            selected = settings.prayer,
            onSelect = { viewModel.updateSettings(settings.copy(prayer = it), viewModel.context) }
        )
    }
}
