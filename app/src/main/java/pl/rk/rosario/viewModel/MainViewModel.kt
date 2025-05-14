package pl.rk.rosario.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.rk.rosario.model.Settings
import pl.rk.rosario.storage.SettingsStore

class MainViewModel(app: Application) : AndroidViewModel(app) {
    /** Application context for resource access */
    private val context: Context get() = getApplication()

    /** Mutable state flow for the current configuration */
    private val _config = MutableStateFlow(Settings())

    /** Public state flow exposing the current configuration */
    val settings: StateFlow<Settings> = _config

    /**
     * Initializes the ViewModel by loading the configuration.
     */
    init {
        viewModelScope.launch {
            // load persisted settings
            SettingsStore.read(context).collect { _config.value = it }
        }
    }
}