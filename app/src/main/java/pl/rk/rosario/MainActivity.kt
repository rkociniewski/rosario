package pl.rk.rosario

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
import pl.rk.rosario.ui.helper.isDarkTheme
import pl.rk.rosario.ui.helper.rememberLocalizedContext
import pl.rk.rosario.ui.rosary.RosaryScreen
import pl.rk.rosario.ui.theme.RosarioTheme
import pl.rk.rosario.viewModel.RosaryViewModel

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
