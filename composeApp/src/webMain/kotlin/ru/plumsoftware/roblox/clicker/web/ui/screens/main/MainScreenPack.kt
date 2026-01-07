package ru.plumsoftware.roblox.clicker.web.ui.screens.main

import ru.plumsoftware.roblox.clicker.web.model.Background
import ru.plumsoftware.roblox.clicker.web.model.GameCharacter
import ru.plumsoftware.roblox.clicker.web.model.GamerData
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.screens_dialogs.MainScreenDialog
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.screens_dialogs.MainScreenScreens

object MainScreenPack {
    sealed interface Event {
        class onSettingsClick : Event

        class onHeroClick : Event
        class onSoundsClick : Event
        class onBackClick : Event
        class onBoostClick : Event

        object onClaimGemsClick : Event // Ивент нажатия на карточку с гемами
        object onCloseDialog : Event // Закрыть диалог

        object onQuestsClick : Event      // Открыть окно заданий
        data class onClaimQuest(val questId: Int) : Event // Забрать награду за задание
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
        val shopScreenName: String,

        val isLoading: Boolean,
        val gamerData: GamerData,
        val charactersList: List<GameCharacter>,

        val clickPowerForGems: Double,
        val maxClickProgressForGems: Double,

        val isStartedBgMusic: Boolean,
        val isSoundOn: Boolean,

        val backgroundsList: List<Background>,
        val totalIncome: Long
    ) {
        companion object {
            fun default() = MainScreenState(
                currentScreen = MainScreenScreens.Shop.HeroShop(),
                currentMainScreenDialog = MainScreenDialog.Empty,
                shopScreenName = "персонажи",

                isLoading = false,
                gamerData = GamerData(),
                charactersList = emptyList(),

                clickPowerForGems = 0.0,
                maxClickProgressForGems = 0.0,

                isStartedBgMusic = false,
                isSoundOn = true,

                backgroundsList = emptyList(),
                totalIncome = 0
            )
        }
    }
}