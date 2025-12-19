package ru.plumsoftware.roblox.clicker.web.ui.screens.main

sealed interface MainScreenDialog {

    object Empty : MainScreenDialog

    sealed interface MainDialog : MainScreenDialog {
        class SettingsDialog(): MainDialog
    }
}