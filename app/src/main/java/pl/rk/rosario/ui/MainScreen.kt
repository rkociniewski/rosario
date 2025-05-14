package pl.rk.rosario.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import pl.rk.rosario.R
import pl.rk.rosario.model.Settings
import pl.rk.rosario.viewModel.MainViewModel

@Suppress("UnusedPrivateProperty")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val initialConfig by viewModel.settings.collectAsState()
    var currentConfig by remember { mutableStateOf(initialConfig) }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            RosarioTopAppBar(
                currentConfig = currentConfig,
                onDrawClick = { },
                onConfigClick = { },
            )
        }
    ) { padding -> println(padding) }
}

@Suppress("UnusedParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RosarioTopAppBar(
    currentConfig: Settings,
    onDrawClick: () -> Unit,
    onConfigClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = { Text(stringResource(R.string.app_name)) },
    )
}