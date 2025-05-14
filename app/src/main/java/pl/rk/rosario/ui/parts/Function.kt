package pl.rk.rosario.ui.parts

import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.rk.rosario.R
import pl.rk.rosario.model.Settings
import pl.rk.rosario.storage.SettingsStore

/** Mutable state flow for the current settings */
val settingsFlow = MutableStateFlow(Settings())

/**
 * Updates the application settings and persists the changes.
 *
 * @param settings The new settings to apply
 */
fun AndroidViewModel.updateSettings(settings: Settings, context: Context) {
    Log.d(
        "pl.rk.rosario",
        "[DEBUG] ${context.getString(R.string.log_debug_settings_updated, settings)}"
    )

    settingsFlow.value = settings
    viewModelScope.launch {
        SettingsStore.write(context, settings)
    }
}