package com.example.fundafirstsub.preferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PreferenceViewModel(private val pref: PreferenceSetting): ViewModel() {
    fun getSettingTheme(): LiveData<Boolean> {
        return pref.getSettingTheme().asLiveData()
    }

    fun saveSettingTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            pref.saveSettingTheme(isDarkMode)
        }
    }
}