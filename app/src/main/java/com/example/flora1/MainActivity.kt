package com.example.flora1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.Theme
import com.example.flora1.navigationroot.NavigationRoot
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preferences: Preferences2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val theme by preferences.theme.collectAsStateWithLifecycle(initialValue = Theme.AUTO)
            val isDark = when (theme) {
                Theme.AUTO -> isSystemInDarkTheme()
                Theme.LIGHT -> false
                Theme.DARK -> true
            }

            Flora1Theme(
                darkTheme = isDark,
            ) {
                NavigationRoot(navController)
            }
        }
    }
}
