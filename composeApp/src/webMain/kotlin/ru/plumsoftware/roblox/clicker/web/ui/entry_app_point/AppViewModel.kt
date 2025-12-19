package ru.plumsoftware.roblox.clicker.web.ui.entry_app_point

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

class AppViewModel : ViewModel() {

    val state =
        MutableStateFlow(MainAppState.MainScreenState.default())

    companion object {
        val koinVM = module {
            viewModelOf(::AppViewModel)
        }
    }
}