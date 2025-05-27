package pl.rk.rosario.ui.prayer

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import pl.rk.rosario.util.Dimensions

@Composable
fun PrayerResetAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    localizedContext: Context
) {
    Dialog(onDismiss, DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(
            Modifier
                .fillMaxWidth(Dimensions.SCREEN_MARGIN)
                .padding(Dimensions.dialogPadding),
            MaterialTheme.shapes.medium,
            tonalElevation = Dimensions.height
        ) {
            Column(
                Modifier
                    .padding(Dimensions.dialogPadding)
                    .verticalScroll(rememberScrollState()),
                Arrangement.spacedBy(Dimensions.itemSpacing)
            ) {
                CompositionLocalProvider(LocalContext provides localizedContext) {
                    PrayerResetScreen(onConfirm, onDismiss)
                }
            }
        }
    }
}
