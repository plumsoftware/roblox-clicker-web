@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.utils

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

actual object GameLifecycle {
    private val _isGameActive = MutableStateFlow(true)
    actual val isGameActive: StateFlow<Boolean> = _isGameActive.asStateFlow()
}