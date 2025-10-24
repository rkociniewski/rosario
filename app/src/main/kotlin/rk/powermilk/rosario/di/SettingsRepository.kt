package rk.powermilk.rosario.di

import android.content.Context
import rk.powermilk.rosario.model.Settings
import rk.powermilk.rosario.util.SettingsStore

class SettingsRepository(private val context: Context) {
    val settingsFlow = SettingsStore.read(context)
    suspend fun updateSettings(settings: Settings) = SettingsStore.write(context, settings)
}
