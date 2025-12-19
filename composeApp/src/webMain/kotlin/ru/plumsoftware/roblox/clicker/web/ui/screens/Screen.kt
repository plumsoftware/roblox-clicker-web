package ru.plumsoftware.roblox.clicker.web.ui.screens

sealed interface Screen {
    sealed interface Main : Screen {
        class Play() : Main
    }
}