package pl.rk.rosario.ui.prayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pl.rk.rosario.R
import pl.rk.rosario.util.Dimensions

@Composable
fun PrayerResetScreen(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Column(
        Modifier.padding(Dimensions.dialogPadding),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.restart_prayer_title),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(Dimensions.dialogPadding))
        Text(
            text = stringResource(R.string.restart_prayer_message),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(Dimensions.spacerHeigt))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.yes))
            }
            Spacer(Modifier.width(Dimensions.height))
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.no))
            }
        }
    }
}
