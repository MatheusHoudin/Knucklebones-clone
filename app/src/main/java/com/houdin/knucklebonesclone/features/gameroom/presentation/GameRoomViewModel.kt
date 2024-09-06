package com.houdin.knucklebonesclone.features.gameroom.presentation

import androidx.lifecycle.ViewModel
import com.houdin.knucklebonesclone.features.gameroom.domain.GameRoomRepository
import com.houdin.knucklebonesclone.features.gameroom.presentation.model.GameRoomState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameRoomViewModel(
    private val gameRoomRepository: GameRoomRepository
) : ViewModel() {

    private val _gameRoomState: MutableStateFlow<GameRoomState> = MutableStateFlow(GameRoomState(""))
    val gameRoomState: StateFlow<GameRoomState> = _gameRoomState

    init {
        createGameRoom()
    }

    private fun createGameRoom() {
        _gameRoomState.value = _gameRoomState.value.copy(
            roomLink = gameRoomRepository.createGameRoom()
        )
    }

}