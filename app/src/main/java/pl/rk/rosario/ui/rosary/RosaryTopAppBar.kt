package pl.rk.rosario.ui.rosary

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Church
import androidx.compose.material.icons.outlined.RemoveCircle
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pl.rk.rosario.R
import pl.rk.rosario.enums.NavigationMode
import pl.rk.rosario.model.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("LongParameterList")
fun RosaryTopAppBar(
    settings: Settings,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onPrayerClick: () -> Unit,
    onReset: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = { Text(stringResource(settings.prayer.label)) },
        actions = {
            IconButton(onClick = onReset) {
                Icon(
                    Icons.Outlined.Replay,
                    contentDescription = stringResource(R.string.reset),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = onPrayerClick) {
                Icon(
                    Icons.Outlined.Church,
                    contentDescription = stringResource(R.string.settings_label_prayer_type),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            if (settings.navigationMode != NavigationMode.TAP) {
                if (settings.allowRewind) {
                    IconButton(onClick = onPreviousClick) {
                        Icon(
                            imageVector = Icons.Outlined.RemoveCircle,
                            contentDescription = stringResource(R.string.action_previous),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                IconButton(onClick = onNextClick) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircle,
                        contentDescription = stringResource(R.string.action_next),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            IconButton(onClick = onSettingsClick) {
                Icon(
                    Icons.Outlined.Settings,
                    contentDescription = stringResource(R.string.settings),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}