package rk.powermilk.rosario.ui.prayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import rk.powermilk.rosario.R
import rk.powermilk.rosario.enums.PrayerType
import rk.powermilk.rosario.model.Settings
import rk.powermilk.rosario.ui.helper.ColumnSelector
import rk.powermilk.rosario.ui.helper.HelpLabel
import rk.powermilk.rosario.util.Dimensions

@Composable
fun PrayerTypeScreen(
    settings: Settings,
    updateSettings: (Settings) -> Unit,
    onClose: () -> Unit,
) {
    Column(
        Modifier.Companion.padding(Dimensions.dialogPadding),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        HelpLabel(
            stringResource(R.string.settings_label_prayer_type),
            stringResource(R.string.tooltip_label_prayer_type)

        )
        ColumnSelector(PrayerType.entries, settings.prayer) {
            updateSettings(settings.copy(prayer = it))
        }

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
