package pl.rk.rosario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.rk.rosario.enums.DisplayMode
import pl.rk.rosario.ui.MainScreen
import pl.rk.rosario.ui.parts.LocalMainViewModel
import pl.rk.rosario.ui.theme.RosarioTheme
import pl.rk.rosario.viewModel.MainViewModel

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
class MainActivity : ComponentActivity() {
    /**
     * Sets up the activity with appropriate theme and content.
     *
     * @param savedInstanceState If the activity is being re-initialized, this Bundle
     * contains the data it most recently supplied in onSaveInstanceState.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            CompositionLocalProvider(LocalMainViewModel provides viewModel) {
                val settings by viewModel.settings.collectAsState()
                val darkTheme = when (settings.displayMode) {
                    DisplayMode.SYSTEM -> isSystemInDarkTheme()
                    DisplayMode.DARK -> true
                    DisplayMode.LIGHT -> false
                }

                RosarioTheme(darkTheme) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}
