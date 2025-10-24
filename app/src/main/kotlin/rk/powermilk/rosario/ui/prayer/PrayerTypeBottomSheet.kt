package rk.powermilk.rosario.ui.prayer

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import rk.powermilk.rosario.model.Settings
import rk.powermilk.rosario.util.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerTypeBottomSheet(
    settings: Settings,
    onSettingsUpdate: (Settings) -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    localizedContext: Context
) {
    ModalBottomSheet(onDismiss, sheetState = sheetState) {
        Surface(
            Modifier
                .fillMaxWidth(Dimensions.SCREEN_MARGIN)
                .padding(Dimensions.dialogPadding),
            MaterialTheme.shapes.medium,
            tonalElevation = Dimensions.height
        ) {
            Column(
                Modifier.Companion
                    .padding(Dimensions.dialogPadding),
                Arrangement.spacedBy(Dimensions.itemSpacing)
            ) {
                CompositionLocalProvider(LocalContext provides localizedContext) {
                    PrayerTypeScreen(settings, onSettingsUpdate, onDismiss)
                }
            }
        }
    }
}
