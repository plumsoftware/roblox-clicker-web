package ru.plumsoftware.roblox.clicker.web.ui.entry_app_point

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.plumsoftware.roblox.clicker.web.ya.YandexGamesManager

class AppViewModel : ViewModel() {

    // Состояние приложения (глобальное)
    val state = MutableStateFlow(MainAppState.MainScreenState.default())

    init {
        initializeApp()
    }

    private fun initializeApp() {
        viewModelScope.launch {
            println("AppViewModel: Start initializing Yandex SDK...")

            // 1. Инициализируем SDK
            YandexGamesManager.init()

            // 2. Как только закончили (неважно успешно или нет), пускаем в игру
            if (YandexGamesManager.isInitialized) {
                println("AppViewModel: SDK Success!")
            } else {
                println("AppViewModel: SDK Failed (Offline mode)")
            }

            // Устанавливаем флаг готовности, чтобы UI переключился на MainScreen
            state.update { it.copy(isYandexReady = true) }
        }
    }

    companion object {
        val koinVM = module {
            viewModelOf(::AppViewModel)
        }
    }
}