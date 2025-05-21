package pl.rk.rosario.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import pl.rk.rosario.enums.DisplayMode
import pl.rk.rosario.enums.Language
import pl.rk.rosario.enums.NavigationMode
import pl.rk.rosario.enums.PrayerLocation
import pl.rk.rosario.enums.PrayerType
import pl.rk.rosario.model.Settings

/**
 * Manages configuration data persistence using Jetpack DataStore preferences.
 * Provides functionality to read and write application configuration settings.
 */
private val Context.dataStore by preferencesDataStore("settings")

/**
 * Object responsible for managing application configuration data.
 * Uses Android's DataStore preferences for persisting configuration values.
 */
object SettingsStore {
    private val logger = AppLogger(this::class.simpleName ?: LogTags.SETTINGS_STORE)

    private val LANGUAGE = stringPreferencesKey("language")
    private val NAVIGATION_MODE = stringPreferencesKey("navigation_mode")
    private val PRAYER_TYPE = stringPreferencesKey("prayer_type")
    private val DISPLAY_MODE = stringPreferencesKey("display_mode")
    private val ALLOW_REWIND = booleanPreferencesKey("allow_rewind")
    private val PRAYER_LOCATION = stringPreferencesKey("prayer_location")

    fun read(context: Context): Flow<Settings> = context.dataStore.data.map {
        val settings = Settings(
            safeEnumValueOf(it[LANGUAGE], Language.EN),
            safeEnumValueOf(it[NAVIGATION_MODE], NavigationMode.TAP),
            safeEnumValueOf(it[PRAYER_TYPE], PrayerType.ROSARY),
            safeEnumValueOf(it[DISPLAY_MODE], DisplayMode.SYSTEM),
            it[ALLOW_REWIND] == true,
            safeEnumValueOf(it[PRAYER_LOCATION], PrayerLocation.BOTTOM),
        )
        logger.debug("Loaded settings: $settings")
        settings
    }.catch {
        logger.error("Error reading settings", it)
        emit(Settings())
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun write(context: Context, settings: Settings) = try {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE] = settings.language.name
            preferences[NAVIGATION_MODE] = settings.navigationMode.name
            preferences[PRAYER_TYPE] = settings.prayer.name
            preferences[DISPLAY_MODE] = settings.displayMode.name
            preferences[ALLOW_REWIND] = settings.allowRewind
            preferences[PRAYER_LOCATION] = settings.prayerLocation.name
            context.getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
                .putString("language", settings.language.name.lowercase())
                .apply()
        }
        true
    } catch (e: Exception) {
        logger.error("Error writing settings", e)
        false
    }
}

// Safe fallback for enums
private inline fun <reified T : Enum<T>> safeEnumValueOf(name: String?, default: T): T {
    return enumValues<T>().firstOrNull { it.name == name } ?: default
}
