package pl.rk.rosario.ui.rosary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.rk.rosario.ui.settings.SettingsDialog
import pl.rk.rosario.viewModel.RosaryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RosaryScreen(
    modifier: Modifier = Modifier,
    viewModel: RosaryViewModel = viewModel()
) {
    val initialSettings by viewModel.settings.collectAsState()
    var currentSetting by remember { mutableStateOf(initialSettings) }
    val beads by viewModel.beads.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val currentPrayer by viewModel.currentPrayer.collectAsState()
    var showSettingsDialog by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

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
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            RosaryTopAppBar(
                currentSetting = currentSetting,
                onSettingsClick = { showSettingsDialog = true },
                onPreviousClick = { viewModel.previous() },
                onNextClick = { viewModel.next() }
            )
        },
        bottomBar = {
            RosaryBottomAppBar(
                prayer = currentPrayer
            )
        },
        modifier = modifier
    ) { padding ->
        RosaryContent(
            beads = beads,
            currentSetting = currentSetting,
            currentIndex = currentIndex,
            modifier = Modifier.padding(padding),
            onPreviousClick = { viewModel.previous() },
            onNextClick = { viewModel.next() }
        )
    }

    if (showSettingsDialog) {
        SettingsDialog(
            initialSettings = initialSettings,
            onSettingsUpdate = {
                currentSetting = it
                viewModel.updateSettings(it)
            },
            onDismiss = {
                showSettingsDialog = false
                viewModel.updateSettings(currentSetting)
            }
        )
    }
}
