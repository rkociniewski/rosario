package pl.rk.rosario.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import pl.rk.rosario.model.Settings

/**
 * Configuration dialog for application settings.
 *
 * This composable displays a modal dialog containing configuration options for the application.
 * It shows the configuration section and any validation error messages.
 *
 * @param initialSettings The original configuration before any changes
 * @param onSettingsUpdate Callback to update the configuration when changes are made
 * @param onDismiss Callback invoked when the dialog is dismissed
 */
@Composable
fun SettingsDialog(
    initialSettings: Settings,
    onSettingsUpdate: (Settings) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SettingsScreen(
                    settings = initialSettings,
                    updateSettings = onSettingsUpdate,
                    onClose = onDismiss
                )
            }
        }
    }
}