package com.houdin.knucklebonesclone

import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.Secure
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
import com.google.firebase.database.FirebaseDatabase
import com.houdin.knucklebonesclone.features.gameroom.presentation.GameRoom
import com.houdin.knucklebonesclone.features.home.presentation.HomePage
import com.houdin.knucklebonesclone.shared.preferences.AppPreferences
import com.houdin.knucklebonesclone.shared.theme.KnucklebonesCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppPreferences.deviceId = Secure.getString(contentResolver, Secure.ANDROID_ID)
        AppPreferences.packageName = packageName

        enableEdgeToEdge()
        setContent {
            KnucklebonesCloneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val a = FirebaseDatabase.getInstance().getReference("Room").push()
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