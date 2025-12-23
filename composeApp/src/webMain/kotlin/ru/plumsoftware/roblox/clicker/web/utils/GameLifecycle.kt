@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.utils

import kotlinx.coroutines.flow.StateFlow

expect object GameLifecycle {
    // true = вкладка открыта, false = вкладка свернута
    val isGameActive: StateFlow<Boolean>
}