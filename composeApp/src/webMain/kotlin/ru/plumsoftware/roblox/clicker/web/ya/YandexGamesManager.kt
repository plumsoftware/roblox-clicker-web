@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.ya

import ru.plumsoftware.roblox.clicker.web.model.GamerData

expect object YandexGamesManager {
    var isInitialized: Boolean
        private set

    suspend fun init()

    // Теперь принимаем и возвращаем целый объект
    suspend fun saveGame(data: GamerData)
    suspend fun loadGame(): GamerData?
}