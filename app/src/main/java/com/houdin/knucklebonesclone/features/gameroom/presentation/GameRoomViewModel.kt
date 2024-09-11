package com.houdin.knucklebonesclone.features.gameroom.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.houdin.knucklebonesclone.features.gameroom.domain.GameRoomRepository
import com.houdin.knucklebonesclone.features.gameroom.presentation.model.GameRoomState
import com.houdin.knucklebonesclone.shared.utils.ROOM_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameRoomViewModel(
    savedStateHandle: SavedStateHandle,
    private val gameRoomRepository: GameRoomRepository
) : ViewModel() {

    private val _gameRoomState: MutableStateFlow<GameRoomState> = MutableStateFlow(GameRoomState(""))
    val gameRoomState: StateFlow<GameRoomState> = _gameRoomState

    init {
        val roomId = savedStateHandle.get<String>(ROOM_ID)
        if (roomId?.isNotEmpty() == true) {
            viewModelScope.launch {
                gameRoomRepository.joinGameRoom(roomId)
            }
        } else {
            createGameRoom()
        }
    }

    private fun createGameRoom() {
        _gameRoomState.value = _gameRoomState.value.copy(
            roomLink = gameRoomRepository.createGameRoom()
        )
    }

}