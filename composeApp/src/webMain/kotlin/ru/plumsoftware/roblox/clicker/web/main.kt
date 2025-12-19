package ru.plumsoftware.roblox.clicker.web

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import ru.plumsoftware.roblox.clicker.web.ui.entry_app_point.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        App()
    }
}