package ru.plumsoftware.roblox.clicker.web.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

class MainScreenViewModel : ViewModel() {

    val effects = MutableSharedFlow<MainScreenPack.Effect>()
    val state = MutableStateFlow(MainScreenPack.MainScreenState.default())

    fun onEvent(event: MainScreenPack.Event) {
        when (event) {
            is MainScreenPack.Event.onSettingsClick -> {
                viewModelScope.launch {
                    state.update {
                        it.copy(currentMainScreenDialog = MainScreenDialog.MainDialog.SettingsDialog())
                    }
                    state.update {
                        it.copy(shopScreenName = getShopName())
                    }
                    effects.emit(MainScreenPack.Effect.onSettingsClicked)
                }
            }

            is MainScreenPack.Event.onBackClick -> {
                viewModelScope.launch {
                    state.update {
                        it.copy(currentScreen = MainScreenScreens.Shop.BackShop())
                    }
                    state.update {
                        it.copy(shopScreenName = getShopName())
                    }
                    effects.emit(MainScreenPack.Effect.onBackClicked)
                }
            }

            is MainScreenPack.Event.onBoostClick -> {
                viewModelScope.launch {
                    state.update {
                        it.copy(currentScreen = MainScreenScreens.Shop.BoostShop())
                    }
                    state.update {
                        it.copy(shopScreenName = getShopName())
                    }
                    effects.emit(MainScreenPack.Effect.onBoostClicked)
                }
            }

            is MainScreenPack.Event.onHeroClick -> {
                viewModelScope.launch {
                    state.update {
                        it.copy(currentScreen = MainScreenScreens.Shop.HeroShop())
                    }
                    state.update {
                        it.copy(shopScreenName = getShopName())
                    }
                    effects.emit(MainScreenPack.Effect.onHeroClicked)
                }
            }

            is MainScreenPack.Event.onSoundsClick -> {
                viewModelScope.launch {
                    state.update {
                        it.copy(currentScreen = MainScreenScreens.Shop.SoundShop())
                    }
                    state.update {
                        it.copy(shopScreenName = getShopName())
                    }
                    effects.emit(MainScreenPack.Effect.onSoundsClicked)
                }
            }
        }
    }

    private fun getShopName() : String {
        return when (state.value.currentScreen) {
            is MainScreenScreens.Shop.BackShop -> "задний фон"
            is MainScreenScreens.Shop.BoostShop -> "бусты и улучшения"
            is MainScreenScreens.Shop.HeroShop -> "персонажи"
            is MainScreenScreens.Shop.SoundShop -> "звуки"
        }
    }

    companion object {
        val koinVM = module {
            viewModelOf(::MainScreenViewModel)
        }
    }
}