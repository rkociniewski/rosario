package pl.rk.rosario.ui.rosary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.DoNotDisturbOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.rk.rosario.R
import pl.rk.rosario.ui.parts.LocalMainViewModel
import pl.rk.rosario.viewModel.RosaryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RosaryScreen(
    modifier: Modifier = Modifier,
    rosaryViewModel: RosaryViewModel = viewModel()
) {
    val mainViewModel = LocalMainViewModel.current
    val settings by mainViewModel.settings.collectAsState()
    val currentIndex by rosaryViewModel.currentIndex.collectAsState()
    val currentPrayer by rosaryViewModel.currentPrayer.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    if (settings.allowRewind) {
                        IconButton(onClick = { rosaryViewModel.previous() }) {
                            Icon(
                                imageVector = Icons.Outlined.DoNotDisturbOn,
                                contentDescription = stringResource(R.string.action_previous)
                            )
                        }
                    }
                    IconButton(onClick = { rosaryViewModel.next() }) {
                        Icon(
                            imageVector = Icons.Outlined.AddCircle,
                            contentDescription = stringResource(R.string.action_next)
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(tonalElevation = 4.dp) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(currentPrayer)
                }
            }
        },
        modifier = modifier
    ) { padding ->
        RosaryCanvas(
            currentIndex = currentIndex,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}