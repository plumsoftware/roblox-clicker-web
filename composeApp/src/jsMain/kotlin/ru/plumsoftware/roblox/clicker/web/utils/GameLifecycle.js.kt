@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.utils

import kotlinx.browser.document
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

actual object GameLifecycle {
    private val _isGameActive = MutableStateFlow(true)
    actual val isGameActive: StateFlow<Boolean> = _isGameActive.asStateFlow()

    init {
        // Проверяем состояние при старте
        updateState()

        // Слушаем изменения
        document.addEventListener("visibilitychange", {
            updateState()
        })
    }

    private fun updateState() {
        // Если hidden == true, значит игра НЕ активна
        val isHidden = document.asDynamic().hidden.unsafeCast<Boolean>()
        _isGameActive.value = !isHidden

        if (isHidden) {
            console.log("[GameLifecycle] Game Paused (Tab hidden)")
        } else {
            console.log("[GameLifecycle] Game Resumed (Tab visible)")
        }
    }
}