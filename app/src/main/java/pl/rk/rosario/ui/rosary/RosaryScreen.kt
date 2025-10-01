package pl.rk.rosario.ui.rosary

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import pl.rk.rosario.enums.PrayerLocation
import pl.rk.rosario.ui.prayer.PrayerResetAlertDialog
import pl.rk.rosario.ui.prayer.PrayerTypeBottomSheet
import pl.rk.rosario.ui.settings.SettingsDialog
import pl.rk.rosario.viewModel.RosaryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RosaryScreen(
    modifier: Modifier = Modifier,
    viewModel: RosaryViewModel = hiltViewModel<RosaryViewModel>(),
    localizedContext: Context
) {
    val settings by viewModel.settings.collectAsState()
    val beads by viewModel.beads.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val currentPrayerId by viewModel.currentPrayerId.collectAsState()
    var showSettingsDialog by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val showRestartDialog by viewModel.shouldShowRestartDialog.collectAsState()
    val view = LocalView.current

    DisposableEffect(Unit) {
        val window = (view.context as? Activity)?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    if (beads.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        return
    }

    Scaffold(
        modifier,
        {
            Column {
                RosaryTopAppBar(
                    settings = settings,
                    onPreviousClick = { viewModel.previous() },
                    onNextClick = { viewModel.next() },
                    onSettingsClick = { showSettingsDialog = true },
                    onPrayerClick = { showBottomSheet = true },
                    onReset = { viewModel.reset(true) }
                )

                PrayerTitle(settings)

                if (settings.prayerLocation == PrayerLocation.TOP) {
                    RosaryBottomAppBar(prayerId = currentPrayerId)
                }
            }
        },
        {
            if (settings.prayerLocation == PrayerLocation.BOTTOM) {
                RosaryBottomAppBar(currentPrayerId)
            }
        },
        { SnackbarHost(snackBarHostState) },
    ) { padding ->
        RosaryContent(
            beads, settings, currentIndex, Modifier.padding(padding),
            { viewModel.previous() }, { viewModel.next() }
        )

        if (showSettingsDialog) {
            SettingsDialog(
                settings = settings, onSettingsUpdate = { viewModel.updateSettings(it) },
                onDismiss = {
                    showSettingsDialog = false
                    viewModel.updateSettings(settings)
                },
                localizedContext = localizedContext
            )
        }

        if (showBottomSheet) {
            PrayerTypeBottomSheet(
                settings, { viewModel.updateSettings(it) },
                {
                    showBottomSheet = false
                    viewModel.updateSettings(settings)
                },
                sheetState,
                localizedContext
            )
        }

        if (showRestartDialog) {
            PrayerResetAlertDialog(
                { viewModel.reset(false) },
                { viewModel.reset(true) },
                localizedContext
            )
        }
    }
}
