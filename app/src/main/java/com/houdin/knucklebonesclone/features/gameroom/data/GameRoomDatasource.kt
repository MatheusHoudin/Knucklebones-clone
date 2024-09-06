package com.houdin.knucklebonesclone.features.gameroom.data

import com.google.firebase.database.FirebaseDatabase
import com.houdin.knucklebonesclone.features.gameroom.model.GameRoom
import com.houdin.knucklebonesclone.shared.preferences.AppPreferences
import com.houdin.knucklebonesclone.shared.utils.FIREBASE_ROOM_KEY

interface GameRoomDatasource {
    fun createGameRoom(): String
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
}