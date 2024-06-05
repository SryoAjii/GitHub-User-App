package com.example.fundafirstsub.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.fundafirstsub.R
import com.example.fundafirstsub.preferences.PreferenceSetting
import com.example.fundafirstsub.preferences.PreferenceViewModel
import com.example.fundafirstsub.preferences.PreferenceViewModelFactory
import com.example.fundafirstsub.preferences.dataStore
import com.example.fundafirstsub.ui.MainActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val pref = PreferenceSetting.getInstance(application.dataStore)
        val preferenceViewModel = ViewModelProvider(this, PreferenceViewModelFactory(pref)).get(
            PreferenceViewModel::class.java
        )

        preferenceViewModel.getSettingTheme().observe(this) { isDarkMode: Boolean ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }



        @Suppress("DEPRECATION")
        Handler().postDelayed(Runnable {
            val intent = Intent(
                this@SplashActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }, 2000)
    }
}