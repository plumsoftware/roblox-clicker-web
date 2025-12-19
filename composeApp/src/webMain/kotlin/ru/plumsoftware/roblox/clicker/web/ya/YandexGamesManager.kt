@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.ya

expect object YandexGamesManager {
    var isInitialized: Boolean
        private set

    suspend fun init()
}