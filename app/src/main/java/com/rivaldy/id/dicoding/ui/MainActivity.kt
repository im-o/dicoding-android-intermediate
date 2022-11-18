package com.rivaldy.id.dicoding.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rivaldy.id.core.utils.UtilExtensions.openActivity
import com.rivaldy.id.dicoding.ui.auth.login.LoginActivity
import com.rivaldy.id.dicoding.ui.home.index.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        checkUserSession()
    }

    private fun checkUserSession() {
        if (viewModel.getUserTokenPref().isNotEmpty()) {
            openActivity(HomeActivity::class.java)
        } else {
            openActivity(LoginActivity::class.java)
        }
        finish()
    }
}