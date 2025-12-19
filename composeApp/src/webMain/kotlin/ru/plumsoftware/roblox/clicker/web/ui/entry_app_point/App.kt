package ru.plumsoftware.roblox.clicker.web.ui.entry_app_point

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import ru.plumsoftware.roblox.clicker.web.ui.screens.Screen
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.MainScreen
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.MainScreenViewModel
import ru.plumsoftware.roblox.clicker.web.ui.theme.AppTheme

@Composable
fun App() {
    KoinApplication(application = {
        // Подключаем наши модули
        modules(
            AppViewModel.koinVM,
            MainScreenViewModel.koinVM
        )
    }) {
        val appViewModel: AppViewModel = koinViewModel()
        val appState = appViewModel.state.collectAsState()

        AppTheme {
            if (!appState.value.isYandexReady) {
                // Показываем экран загрузки (спиннер)
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Загружаем ресурсы...", style = MaterialTheme.typography.headlineMedium)
                }
            } else {
                when (appState.value.currentScreen) {
                    is Screen.Main.Play -> {
                        MainScreen()
                    }
                }
            }
        }
    }
}