package ru.plumsoftware.roblox.clicker.web.ui.screens.main.screens_dialogs

sealed interface MainScreenScreens {
    sealed interface Shop : MainScreenScreens {
        class HeroShop(
            val name: String = "персонажи"
        ) : Shop
        class BoostShop(
            val name: String = "бусты и улучшения"
        ) : Shop
        class BackShop(
            val name: String = "задний фон"
        ) : Shop
        class SoundShop(
            val name: String = "звуки"
        ) : Shop
    }
}