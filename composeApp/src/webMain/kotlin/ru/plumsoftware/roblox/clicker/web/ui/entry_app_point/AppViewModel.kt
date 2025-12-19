package ru.plumsoftware.roblox.clicker.web.ui.entry_app_point

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.plumsoftware.roblox.clicker.web.ya.YandexGamesManager

class AppViewModel : ViewModel() {

    val state =
        MutableStateFlow(MainAppState.MainScreenState.default())

    init {
        initializeApp()
    }

    private fun initializeApp() {
        // Запускаем корутину в скоупе ViewModel
        viewModelScope.launch {
            println("AppViewModel: Start initializing Yandex SDK...")

            // 1. Инициализируем SDK
            YandexGamesManager.init()

            // 2. Обновляем стейт, когда закончили
            if (YandexGamesManager.isInitialized) {
                println("AppViewModel: SDK Success! Loading player data...")

                // ТУТ МОЖНО ЗАГРУЗИТЬ СОХРАНЕНИЯ
                // val saves = YandexGamesManager.loadData() ...

                state.update { it.copy(isYandexReady = true) }
            } else {
                println("AppViewModel: SDK Failed (Offline mode?)")
                // Можно тоже поставить true, чтобы пустить игрока играть без облака
                state.update { it.copy(isYandexReady = true) }
            }
        }
    }

    companion object {
        val koinVM = module {
            viewModelOf(::AppViewModel)
        }
    }
}