package ru.plumsoftware.roblox.clicker.web.ui.entry_app_point

import androidx.compose.runtime.*
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
            when (appState.value.currentScreen) {
                is Screen.Main.Play -> {
                    MainScreen()
                }
            }
        }
    }
}