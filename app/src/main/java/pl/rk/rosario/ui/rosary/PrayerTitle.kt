package pl.rk.rosario.ui.rosary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import pl.rk.rosario.R
import pl.rk.rosario.model.Settings
import pl.rk.rosario.util.Dimensions

@Composable
fun PrayerTitle(settings: Settings) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = Dimensions.dialogPadding),
        color = MaterialTheme.colorScheme.onPrimary,
        text = stringResource(R.string.selected_prayer) + ": " + stringResource(settings.prayer.label),
        style = MaterialTheme.typography.titleSmall,
        textAlign = TextAlign.Center
    )
}
