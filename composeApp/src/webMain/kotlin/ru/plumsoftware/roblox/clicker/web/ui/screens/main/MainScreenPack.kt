package ru.plumsoftware.roblox.clicker.web.ui.screens.main

object MainScreenPack {
    sealed interface Event {
        class onSettingsClick : Event

        class onHeroClick : Event
        class onSoundsClick : Event
        class onBackClick : Event
        class onBoostClick : Event
    }

    sealed interface Effect {
        object onSettingsClicked : Effect

        object onHeroClicked : Effect
        object onSoundsClicked : Effect
        object onBackClicked : Effect
        object onBoostClicked : Effect
    }

    data class MainScreenState(
        val currentScreen: MainScreenScreens,
        val currentMainScreenDialog: MainScreenDialog,
        val shopScreenName: String
    ) {
        companion object {
            fun default() = MainScreenState(
                currentScreen = MainScreenScreens.Shop.HeroShop(),
                currentMainScreenDialog = MainScreenDialog.Empty,
                shopScreenName = "персонажи"
            )
        }
    }
}