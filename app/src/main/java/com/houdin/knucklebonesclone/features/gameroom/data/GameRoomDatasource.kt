package com.houdin.knucklebonesclone.features.gameroom.data

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.houdin.knucklebonesclone.features.gameroom.model.GameRoom
import com.houdin.knucklebonesclone.shared.preferences.AppPreferences
import com.houdin.knucklebonesclone.shared.utils.FIREBASE_ROOM_KEY
import kotlinx.coroutines.tasks.await

interface GameRoomDatasource {
    fun createGameRoom(): String
    suspend fun joinGameRoom(roomId: String)
}

class GameRoomDatasourceImpl : GameRoomDatasource {

    private val firebaseInstance = FirebaseDatabase.getInstance()

    override fun createGameRoom(): String {
        val gameRoom = GameRoom(
            player1 = AppPreferences.deviceId
        )
        val roomReference = firebaseInstance.getReference(FIREBASE_ROOM_KEY).push()
        val roomKey = roomReference.key

        roomReference.setValue(gameRoom)

        return roomKey.orEmpty()
    }

    override suspend fun joinGameRoom(roomId: String) {
        val gameRoom = (firebaseInstance.getReference(FIREBASE_ROOM_KEY).child(roomId).get()
            .await().value as HashMap<*, *>).let {
            GameRoom.fromMap(it)
        }

        //The player is not in the game room, we should add them
        if (gameRoom.player1.isEmpty() && gameRoom.player2 != AppPreferences.deviceId) {
            firebaseInstance.getReference(FIREBASE_ROOM_KEY).child(roomId).child("player1")
                .setValue(AppPreferences.deviceId)
        } else if (gameRoom.player2.isEmpty() && gameRoom.player1 != AppPreferences.deviceId) {
            firebaseInstance.getReference(FIREBASE_ROOM_KEY).child(roomId).child("player2")
                .setValue(AppPreferences.deviceId)
        }

        startGame(roomId)
    }

    private suspend fun startGame(roomId: String) {
        val gameRoom = (firebaseInstance.getReference(FIREBASE_ROOM_KEY).child(roomId).get()
            .await().value as HashMap<*, *>).let {
            GameRoom.fromMap(it)
        }
        val players = listOf(gameRoom.player1, gameRoom.player2)

        if (players.contains(AppPreferences.deviceId)
            && (gameRoom.player1.isNotEmpty() && gameRoom.player2.isNotEmpty())
            && (gameRoom.player1 != gameRoom.player2)) {
            Log.d("CHARLAO", "startGame: $gameRoom")
            firebaseInstance.getReference(FIREBASE_ROOM_KEY).child(roomId).child("gameStarted")
                .setValue(true)
        }
    }
}