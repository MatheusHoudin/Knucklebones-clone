package com.houdin.knucklebonesclone.features.gameroom.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.houdin.knucklebonesclone.features.gameroom.domain.GameRoomRepository
import com.houdin.knucklebonesclone.features.gameroom.model.GameRoom
import com.houdin.knucklebonesclone.features.gameroom.presentation.model.GameRoomState
import com.houdin.knucklebonesclone.shared.utils.ROOM_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GameRoomViewModel(
    savedStateHandle: SavedStateHandle,
    private val gameRoomRepository: GameRoomRepository
) : ViewModel() {

    private val _gameRoomState: MutableStateFlow<GameRoomState> =
        MutableStateFlow(GameRoomState(""))
    val gameRoomState: StateFlow<GameRoomState> = _gameRoomState

    private var _navigateToGamePage = MutableStateFlow(false)
    var navigateToGamePage : StateFlow<Boolean> = _navigateToGamePage.asStateFlow()

    private val firebaseInstance = FirebaseDatabase.getInstance()

    init {
        val roomId = savedStateHandle.get<String>(ROOM_ID)
        if (roomId?.isNotEmpty() == true) {
            viewModelScope.launch {
                gameRoomRepository.joinGameRoom(roomId)
                observeGameChanged(roomId)
            }
        } else {
            viewModelScope.launch {
                createGameRoom()
            }
        }
    }

    private fun createGameRoom() {
        val gameRoomState = gameRoomRepository.createGameRoom()
        _gameRoomState.value = _gameRoomState.value.copy(
            roomLink = gameRoomState.roomLink,
            roomId = gameRoomState.roomId
        )
        observeGameChanged(gameRoomState.roomId)
    }

    private fun observeChanged(roomId: String): Flow<GameRoom> =
        firebaseInstance
            .getReference("room")
            .child(roomId)
            .observeValue()
            .map {
                val map = it?.value as HashMap<*, *>
                GameRoom(
                    player1 = map["player1"] as String,
                    player2 = map["player2"] as String,
                    gameStarted = map["gameStarted"] as Boolean
                )
            }

    private fun observeGameChanged(roomId: String) {
        observeChanged(roomId).asLiveData(viewModelScope.coroutineContext + Dispatchers.Default).observeForever {
            Log.d("CHARLAO", "observeChanged: $it")
            if (it.gameStarted) {
                _navigateToGamePage.value = true
            }
        }
    }

    private fun DatabaseReference.observeValue(): Flow<DataSnapshot?> =
        callbackFlow {
            val listener = object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(snapshot).isSuccess
                }
            }
            addValueEventListener(listener)
            awaitClose { removeEventListener(listener) }
        }
}
