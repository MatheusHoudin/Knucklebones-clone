package com.houdin.knucklebonesclone.shared.di

import com.houdin.knucklebonesclone.features.gameroom.data.GameRoomDatasource
import com.houdin.knucklebonesclone.features.gameroom.data.GameRoomDatasourceImpl
import com.houdin.knucklebonesclone.features.gameroom.domain.GameRoomRepository
import com.houdin.knucklebonesclone.features.gameroom.domain.GameRoomRepositoryImpl
import com.houdin.knucklebonesclone.features.gameroom.presentation.GameRoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { GameRoomViewModel(gameRoomRepository = get(), savedStateHandle = get()) }
    single<GameRoomDatasource> { GameRoomDatasourceImpl() }
    single<GameRoomRepository> { GameRoomRepositoryImpl(get()) }
}