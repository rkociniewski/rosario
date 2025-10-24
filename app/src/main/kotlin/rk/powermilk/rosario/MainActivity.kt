package rk.powermilk.rosario

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import rk.powermilk.rosario.ui.helper.isDarkTheme
import rk.powermilk.rosario.ui.helper.rememberLocalizedContext
import rk.powermilk.rosario.ui.rosary.RosaryScreen
import rk.powermilk.rosario.ui.theme.RosarioTheme
import rk.powermilk.rosario.viewModel.RosaryViewModel

/**
 * MainActivity.kt
 *
 * The application's main entry point activity.
 * Sets up the UI theme based on configuration and initializes the main pericope screen.
 */

/**
 * Main activity that initializes the application UI.
 * Sets up the theme based on user preferences and displays the pericope screen.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        enableEdgeToEdge()

        setContent {
            val viewModel = hiltViewModel<RosaryViewModel>()
            val settings by viewModel.settings.collectAsState()

            val darkTheme = settings.displayMode.isDarkTheme()

            val localizedContext = rememberLocalizedContext(settings.language.name.lowercase())
            CompositionLocalProvider(LocalContext provides localizedContext) {
                RosarioTheme(darkTheme) {
                    RosaryScreen(viewModel = viewModel, localizedContext = localizedContext)
                }
            }
        }
    }
}
