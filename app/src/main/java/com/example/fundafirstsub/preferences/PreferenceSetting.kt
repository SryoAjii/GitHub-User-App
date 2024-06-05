package com.example.fundafirstsub.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Setting")

class PreferenceSetting private constructor(private val dataStore:DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getSettingTheme(): Flow<Boolean> {
        return dataStore.data.map { preference ->
            preference[THEME_KEY] ?: false
        }
    }

    suspend fun saveSettingTheme(isDarkMode: Boolean) {
        dataStore.edit { preference ->
            preference[THEME_KEY] = isDarkMode
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PreferenceSetting? = null

        fun getInstance(dataStore: DataStore<Preferences>): PreferenceSetting {
            return INSTANCE ?: synchronized(this) {
                val instance = PreferenceSetting(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}