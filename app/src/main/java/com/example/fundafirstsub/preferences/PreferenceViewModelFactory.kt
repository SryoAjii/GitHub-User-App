package com.example.fundafirstsub.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PreferenceViewModelFactory(private val pref: PreferenceSetting): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreferenceViewModel::class.java)) {
            return PreferenceViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}