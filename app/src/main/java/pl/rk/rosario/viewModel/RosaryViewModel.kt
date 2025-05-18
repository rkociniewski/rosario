package pl.rk.rosario.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.rk.rosario.R
import pl.rk.rosario.enums.PrayerType
import pl.rk.rosario.model.Bead
import pl.rk.rosario.model.Settings
import pl.rk.rosario.storage.SettingsStore
import pl.rk.rosario.ui.parts.generateChotkaBeads
import pl.rk.rosario.ui.parts.generateDivineMercyBeads
import pl.rk.rosario.ui.parts.generateRosaryBeads

class RosaryViewModel(app: Application) : AndroidViewModel(app) {
    private val context = getApplication<Application>()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    /** Mutable state flow for the current configuration */
    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> = _settings

    /** Public state flow exposing the current configuration */
    private val _beads =
        MutableStateFlow<List<Bead>>(generateRosaryBeads()) // Initialize with default beads
    val beads: StateFlow<List<Bead>> = _beads.asStateFlow()

    private val _currentPrayer = MutableStateFlow("")
    val currentPrayer: StateFlow<String> = _currentPrayer.asStateFlow()

    init {
        viewModelScope.launch {
            SettingsStore.read(context).collect { settings ->
                _settings.value = settings
                updateBeadsFromSettings(settings)
                updateCurrentPrayer()
            }
        }
    }

    private fun updateBeadsFromSettings(settings: Settings) {
        _beads.value = when (settings.prayer) {
            PrayerType.ROSARY -> generateRosaryBeads()
            PrayerType.DIVINE_MERCY -> generateDivineMercyBeads()
            PrayerType.JESUS_PRAYER -> generateChotkaBeads()
        }
        _currentIndex.value = 0
    }

    fun updateSettings(settings: Settings) {
        viewModelScope.launch {
            val writeSuccess = SettingsStore.write(context, settings)

            Log.d(
                "rk.gac",
                "[DEBUG] ${context.getString(R.string.log_debug_settings_updated, settings)}"
            )

            if (writeSuccess) {
                _settings.value = settings
                updateBeadsFromSettings(settings)
                Log.d("RosaryViewModel", "Settings updated successfully")
            } else {
                Log.e("RosaryViewModel", "Failed to persist settings")
            }
        }
    }

    fun next() {
        val currentBeads = _beads.value
        if (currentBeads.isNotEmpty() && _currentIndex.value < currentBeads.lastIndex) {
            _currentIndex.value += 1
            updateCurrentPrayer()
        }
    }

    fun previous() {
        if (_beads.value.isNotEmpty() && _currentIndex.value > 0) {
            _currentIndex.value -= 1
            updateCurrentPrayer()
        }
    }

    fun reset() {
        _currentIndex.value = 0
        updateCurrentPrayer()
    }

    private fun updateCurrentPrayer() {
        val beadsList = _beads.value
        val index = _currentIndex.value

        if (beadsList.isNotEmpty() && index < beadsList.size) {
            val bead = beadsList[index]
            _currentPrayer.value = if (bead.prayerId != 0) {
                context.getString(bead.prayerId)
            } else {
                ""
            }
        }
    }
}