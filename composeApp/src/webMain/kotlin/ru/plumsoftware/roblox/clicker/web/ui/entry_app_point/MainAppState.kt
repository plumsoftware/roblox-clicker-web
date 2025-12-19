package ru.plumsoftware.roblox.clicker.web.ui.entry_app_point

import ru.plumsoftware.roblox.clicker.web.ui.screens.Screen

object MainAppState {
    data class MainScreenState (
        val currentScreen: Screen,
    ) {
        companion object {
            fun default() = MainScreenState(
                currentScreen = Screen.Main.Play(),
            )
        }
    }
}