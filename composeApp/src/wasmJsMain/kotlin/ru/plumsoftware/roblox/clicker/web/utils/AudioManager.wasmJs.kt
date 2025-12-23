@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.utils

actual object AudioManager {
    actual var isSoundEnabled: Boolean = true
    actual var isMusicEnabled: Boolean = true

    actual fun playSound(fileName: String, volume: Double) {
        // Пусто (или реализация через AudioPlayer, если захочешь)
        println("Sound play (Wasm stub): $fileName")
    }

    actual fun playMusic(fileName: String, volume: Double) {
        println("Music play (Wasm stub): $fileName")
    }

    actual fun stopMusic() {
    }

    actual fun pauseMusic() {}
    actual fun resumeMusic() {}
}