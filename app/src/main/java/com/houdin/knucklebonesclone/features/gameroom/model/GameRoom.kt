package com.houdin.knucklebonesclone.features.gameroom.model

data class GameRoom(
    val player1: String = "",
    val player2: String = ""
) {

    companion object {
        fun fromMap(map: Map<*, *>) = GameRoom(
            player1 = map["player1"] as String,
            player2 = map["player2"] as String
        )
    }
}