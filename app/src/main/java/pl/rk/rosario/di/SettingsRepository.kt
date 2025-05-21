package pl.rk.rosario.di

import android.content.Context
import pl.rk.rosario.model.Settings
import pl.rk.rosario.util.SettingsStore

class SettingsRepository(private val context: Context) {
    val settingsFlow = SettingsStore.read(context)
    suspend fun updateSettings(settings: Settings) = SettingsStore.write(context, settings)
}