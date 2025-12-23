@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.utils

expect object AudioManager {

    var isSoundEnabled: Boolean
    var isMusicEnabled: Boolean

    fun playSound(fileName: String, volume: Double = 0.5)
    fun playMusic(fileName: String, volume: Double = 0.3)
    fun stopMusic()
}