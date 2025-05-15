package pl.rk.rosario.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.rk.rosario.enums.BeadRole
import pl.rk.rosario.enums.PrayerType
import pl.rk.rosario.model.Bead
import pl.rk.rosario.storage.SettingsStore
import pl.rk.rosario.ui.parts.generateChapletBeads
import pl.rk.rosario.ui.parts.generateChotkaBeads
import pl.rk.rosario.ui.parts.generateRosaryBeads
import pl.rk.rosario.ui.parts.loadPrayerTextMap

class RosaryViewModel(app: Application) : AndroidViewModel(app) {
    private val context = getApplication<Application>()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private lateinit var beads: List<Bead>

    private val _prayers = MutableStateFlow<Map<BeadRole, String>>(emptyMap())
    val prayers: StateFlow<Map<BeadRole, String>> = _prayers.asStateFlow()

    val currentPrayer: StateFlow<String> = combine(currentIndex, prayers) { index, map ->
        val role = beads.getOrNull(index)?.role
        role?.let { map[it] } ?: ""
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    init {
        viewModelScope.launch {
            SettingsStore.read(context).collect {
                beads = when (it.prayer) {
                    PrayerType.ROSARY -> generateRosaryBeads()
                    PrayerType.DIVINE_MERCY -> generateChapletBeads()
                    PrayerType.JESUS_PRAYER -> generateChotkaBeads()
                }

                val rawResId = getRawRes("${it.prayer.name}_${it.language.name}".lowercase())

                if (rawResId != null) {
                    _prayers.value = loadPrayerTextMap(context, rawResId)
                }
            }
        }
    }

    fun next() {
        viewModelScope.launch {
            if (_currentIndex.value < beads.lastIndex) {
                _currentIndex.value += 1
            }
        }
    }

    fun previous() {
        viewModelScope.launch {
            if (_currentIndex.value > 0) {
                _currentIndex.value -= 1
            }
        }
    }

    fun reset() {
        _currentIndex.value = 0
    }

    private fun getRawRes(name: String): Int? {
        val resId = context.resources.getIdentifier(name, "raw", context.packageName)
        return if (resId != 0) resId else null
    }
}
