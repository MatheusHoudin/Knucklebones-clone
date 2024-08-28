package com.houdin.knucklebonesclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.houdin.knucklebonesclone.features.gameroon.presentation.GameRoom
import com.houdin.knucklebonesclone.features.home.presentation.HomePage
import com.houdin.knucklebonesclone.shared.theme.KnucklebonesCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KnucklebonesCloneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = HOME_ROUTE,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(HOME_ROUTE) {
                            HomePage {
                                navController.navigate(GAME_ROOM_ROUTE)
                            }
                        }
                        composable(GAME_ROOM_ROUTE) { GameRoom() }
                    }
                }
            }
        }
    }

    companion object {
        const val HOME_ROUTE = "home"
        const val GAME_ROOM_ROUTE = "gameroom"
    }
}