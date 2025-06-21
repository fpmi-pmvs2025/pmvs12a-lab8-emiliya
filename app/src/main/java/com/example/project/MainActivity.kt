package com.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.project.navigation.AppNavGraph
import com.example.project.ui.theme.SmartNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            SmartNotesTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                AppNavGraph(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}