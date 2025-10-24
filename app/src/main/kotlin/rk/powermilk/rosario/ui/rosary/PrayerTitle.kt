package rk.powermilk.rosario.ui.rosary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import rk.powermilk.rosario.R
import rk.powermilk.rosario.model.Settings
import rk.powermilk.rosario.util.Dimensions

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
