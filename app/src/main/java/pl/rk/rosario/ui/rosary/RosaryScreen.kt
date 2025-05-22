package pl.rk.rosario.ui.rosary

import android.content.Context
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import pl.rk.rosario.enums.PrayerLocation
import pl.rk.rosario.ui.prayer.PrayerTypeBottomSheet
import pl.rk.rosario.ui.settings.SettingsDialog
import pl.rk.rosario.viewModel.RosaryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RosaryScreen(
    modifier: Modifier = Modifier,
    viewModel: RosaryViewModel = hiltViewModel(),
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

    // Add safety check for beads
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
                    settings, { viewModel.previous() }, { viewModel.next() },
                    { showSettingsDialog = true }, { showBottomSheet = true }
                )

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
    ) {
        RosaryContent(
            beads, settings, currentIndex, Modifier.padding(it),
            { viewModel.previous() }, { viewModel.next() }
        )

        if (showSettingsDialog) {
            SettingsDialog(
                settings, { viewModel.updateSettings(it) },
                {
                    showSettingsDialog = false
                    viewModel.updateSettings(settings)
                },
                localizedContext
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
    }
}
