package ru.plumsoftware.roblox.clicker.web.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.screens_dialogs.MainScreenDialog
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.screens_dialogs.MainScreenScreens
import ru.plumsoftware.roblox.clicker.web.ya.YandexGamesManager

class MainScreenViewModel : ViewModel() {

    val effects = MutableSharedFlow<MainScreenPack.Effect>()

    // State
    val state = MutableStateFlow(MainScreenPack.MainScreenState.default())

    init {
        loadProgress()
        startAutoSave()
    }

    // --- ЛОГИКА ДАННЫХ (ЭКОНОМИКА) ---

    private fun loadProgress() {
        viewModelScope.launch {
            while (!YandexGamesManager.isInitialized) {
                delay(100)
            }

            println("MainVM: Loading player data...")
            val loadedData = YandexGamesManager.loadGame()

            if (loadedData != null) {
                state.update { it.copy(gamerData = loadedData) }
                println("MainVM: Data loaded! Coins: ${loadedData.coins}")
            }
        }
    }

    private fun startAutoSave() {
        viewModelScope.launch {
            while (true) {
                delay(30_000)
                if (YandexGamesManager.isInitialized) {
                    YandexGamesManager.saveGame(state.value.gamerData)
                    println("MainVM: Auto-saved!")
                }
            }
        }
    }

    fun onMainCharacterClick() {
        state.update { oldState ->
            val oldData = oldState.gamerData
            val newData = oldData.copy(
                coins = oldData.coins + 1
            )
            oldState.copy(gamerData = newData)
        }
    }

    // --- ЛОГИКА UI (НАВИГАЦИЯ) ---

    fun onEvent(event: MainScreenPack.Event) {
        when (event) {
            is MainScreenPack.Event.onSettingsClick -> {
                updateScreenState(
                    dialog = MainScreenDialog.MainDialog.SettingsDialog(),
                    effect = MainScreenPack.Effect.onSettingsClicked
                )
            }

            is MainScreenPack.Event.onBackClick -> {
                updateScreenState(
                    screen = MainScreenScreens.Shop.BackShop(),
                    effect = MainScreenPack.Effect.onBackClicked
                )
            }

            is MainScreenPack.Event.onBoostClick -> {
                updateScreenState(
                    screen = MainScreenScreens.Shop.BoostShop(),
                    effect = MainScreenPack.Effect.onBoostClicked
                )
            }

            is MainScreenPack.Event.onHeroClick -> {
                updateScreenState(
                    screen = MainScreenScreens.Shop.HeroShop(),
                    effect = MainScreenPack.Effect.onHeroClicked
                )
            }

            is MainScreenPack.Event.onSoundsClick -> {
                updateScreenState(
                    screen = MainScreenScreens.Shop.SoundShop(),
                    effect = MainScreenPack.Effect.onSoundsClicked
                )
            }
        }
    }

    private fun updateScreenState(
        screen: MainScreenScreens.Shop? = null,
        dialog: MainScreenDialog.MainDialog? = null,
        effect: MainScreenPack.Effect
    ) {
        viewModelScope.launch {
            state.update { currentState ->
                var newState = currentState

                if (screen != null) {
                    newState = newState.copy(currentScreen = screen)
                }
                if (dialog != null) {
                    newState = newState.copy(currentMainScreenDialog = dialog)
                }

                // Теперь здесь ошибки не будет
                newState.copy(shopScreenName = getShopName(newState.currentScreen))
            }
            effects.emit(effect)
        }
    }

    // ИСПРАВЛЕНО ЗДЕСЬ:
    // 1. Принимаем общий тип MainScreenScreens (а не Shop)
    // 2. Добавили ветку else для экранов, которые не являются магазином
    private fun getShopName(currentScreen: MainScreenScreens) : String {
        return when (currentScreen) {
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