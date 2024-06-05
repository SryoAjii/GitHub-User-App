package com.example.fundafirstsub.preferences

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.fundafirstsub.databinding.ActivityPreferenceBinding

class PreferenceActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPreferenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val themeSwitch = binding.themeSwitch

        val pref = PreferenceSetting.getInstance(application.dataStore)
        val preferenceViewModel = ViewModelProvider(this, PreferenceViewModelFactory(pref)).get(
            PreferenceViewModel::class.java
        )

        preferenceViewModel.getSettingTheme().observe(this) { isDarkMode: Boolean ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeSwitch.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeSwitch.isChecked = false
            }
        }

        themeSwitch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            preferenceViewModel.saveSettingTheme(isChecked)
        }
    }
}