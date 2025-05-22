package pl.rk.rosario.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.rk.rosario.di.SettingsRepository
import pl.rk.rosario.model.Bead
import pl.rk.rosario.model.Settings
import pl.rk.rosario.ui.parts.generateBeads
import pl.rk.rosario.util.AppLogger
import pl.rk.rosario.util.LogTags

@HiltViewModel
class RosaryViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val logger = AppLogger(this::class.simpleName ?: LogTags.ROSARY_VIEW_MODEL)

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> = _settings

    private val _beads =
        MutableStateFlow<List<Bead>>(settings.value.prayer.generateBeads()) // Initialize with default beads
    val beads: StateFlow<List<Bead>> = _beads.asStateFlow()

    private val _currentPrayerId = MutableStateFlow(0)
    val currentPrayerId: StateFlow<Int> = _currentPrayerId.asStateFlow()

    private val _shouldShowRestartDialog = MutableStateFlow(false)
    val shouldShowRestartDialog: StateFlow<Boolean> = _shouldShowRestartDialog.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.settingsFlow.collect {
                _settings.value = it
                updateBeadsFromSettings(it)
                updateCurrentPrayer()
            }
        }
    }

    private fun updateBeadsFromSettings(settings: Settings) {
        _beads.value = settings.prayer.generateBeads()
        _currentIndex.value = _beads.value.first { it.prayerId != 0 }.index
    }

    fun updateSettings(settings: Settings) {
        viewModelScope.launch {
            val writeSuccess = settingsRepository.updateSettings(settings)

            if (writeSuccess) {
                _settings.value = settings
                updateBeadsFromSettings(settings)

                logger.debug("Settings updated: $settings")
            } else {
                logger.error("Failed to persist settings")
            }
        }
    }

    fun next() {
        val currentBeads = _beads.value
        val startIndex = _currentIndex.value

        val nextIndex =
            (startIndex + 1..currentBeads.lastIndex).firstOrNull { currentBeads[it].prayerId != 0 }

        if (nextIndex != null) {
            _currentIndex.value = nextIndex
            updateCurrentPrayer()
        } else {
            _shouldShowRestartDialog.value = true
        }
    }

    fun previous() {
        val currentBeads = _beads.value
        val startIndex = _currentIndex.value

        val previousIndex = (startIndex - 1 downTo 0).firstOrNull {
            currentBeads[it].prayerId != 0
        }

        if (previousIndex != null) {
            _currentIndex.value = previousIndex
            updateCurrentPrayer()
        }
    }

    fun reset(confirmed: Boolean) {
        if (confirmed) {
            _currentIndex.value = _beads.value.first { it.prayerId != 0 }.index
            updateCurrentPrayer()
        }
        _shouldShowRestartDialog.value = false
    }

    private fun updateCurrentPrayer() {
        val beadsList = _beads.value
        val index = _currentIndex.value

        if (beadsList.isNotEmpty() && index < beadsList.size) {
            val bead = beadsList[index]
            _currentPrayerId.value = bead.prayerId
        } else {
            _currentPrayerId.value = 0
        }
    }
}


