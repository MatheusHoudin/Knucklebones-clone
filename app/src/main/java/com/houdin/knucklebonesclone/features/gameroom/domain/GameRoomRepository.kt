package com.houdin.knucklebonesclone.features.gameroom.domain

import com.houdin.knucklebonesclone.features.gameroom.data.GameRoomDatasource
import com.houdin.knucklebonesclone.features.gameroom.presentation.model.GameRoomState
import com.houdin.knucklebonesclone.shared.preferences.AppPreferences
import com.houdin.knucklebonesclone.shared.utils.APP_ENDPOINT
import com.houdin.knucklebonesclone.shared.utils.FIREBASE_ROOM_KEY

interface GameRoomRepository {
    fun createGameRoom(): GameRoomState
    suspend fun joinGameRoom(roomId: String)
}

class GameRoomRepositoryImpl(
    private val datasource: GameRoomDatasource
) : GameRoomRepository {

    override fun createGameRoom(): GameRoomState = datasource.createGameRoom().let {
        GameRoomState(
            roomLink = "$APP_ENDPOINT/$FIREBASE_ROOM_KEY/$it",
            roomId = it
        )
    }


    override suspend fun joinGameRoom(roomId: String) = datasource.joinGameRoom(roomId)
}