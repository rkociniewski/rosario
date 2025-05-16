package pl.rk.rosario.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.rk.rosario.R
import pl.rk.rosario.enums.BeadRole
import pl.rk.rosario.enums.PrayerType
import pl.rk.rosario.model.Bead
import pl.rk.rosario.model.Settings
import pl.rk.rosario.storage.SettingsStore
import pl.rk.rosario.ui.parts.generateChapletBeads
import pl.rk.rosario.ui.parts.generateChotkaBeads
import pl.rk.rosario.ui.parts.generateRosaryBeads

class RosaryViewModel(app: Application) : AndroidViewModel(app) {
    private val context = getApplication<Application>()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    /** Mutable state flow for the current configuration */
    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> = _settings

    /** Public state flow exposing the current configuration */
    private val _beads = MutableStateFlow<List<Bead>>(emptyList())
    val beads: StateFlow<List<Bead>> = _beads.asStateFlow()

    private val _currentPrayer = MutableStateFlow("")
    val currentPrayer: StateFlow<String> = _currentPrayer.asStateFlow()

    private val _prayers = MutableStateFlow<Map<BeadRole, String>>(emptyMap())
    val prayers: StateFlow<Map<BeadRole, String>> = _prayers.asStateFlow()

    // Use a cached mapping of prayer resource IDs to avoid looking them up repeatedly
    private val prayerResourceCache = mutableMapOf<String, Int?>()

    init {
        viewModelScope.launch {
            // Collect settings changes once and handle downstream effects
            SettingsStore.read(context).collect { settings ->
                beads.collect {
                    _beads.value = when (settings.prayer) {
                        PrayerType.ROSARY -> generateRosaryBeads()
                        PrayerType.DIVINE_MERCY -> generateChapletBeads()
                        PrayerType.JESUS_PRAYER -> generateChotkaBeads()
                    }
                }

                _settings.value = settings
                updateForSettings(settings)
            }
        }
    }

    private fun updateForSettings(settings: Settings) {
        Log.d(
            "rk.gac",
            "[DEBUG] ${context.getString(R.string.log_debug_settings_updated, settings)}"
        )

        _settings.value = settings

        viewModelScope.launch {
            SettingsStore.write(context, settings)

            _beads.value = when (settings.prayer) {
                PrayerType.ROSARY -> generateRosaryBeads()
                PrayerType.DIVINE_MERCY -> generateChapletBeads()
                PrayerType.JESUS_PRAYER -> generateChotkaBeads()
            }
        }
    }

    fun next() {
        val currentBeads = _beads.value
        if (_currentIndex.value < currentBeads.lastIndex) {
            _currentIndex.value += 1
        }
    }

    fun previous() {
        if (_currentIndex.value > 0) {
            _currentIndex.value -= 1
        }
    }

    fun reset() {
        _currentIndex.value = 0
    }

    fun updateSettings(newSettings: Settings) {
        if (_settings.value == newSettings) return
        viewModelScope.launch {
            SettingsStore.write(context, newSettings)
            // Note: no need to update _settings.value here as it will be updated by the collection from SettingsStore
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun getRawRes(name: String): Int? {
        val resId = context.resources.getIdentifier(name, "raw", context.packageName)
        return if (resId != 0) resId else null
    }
}
