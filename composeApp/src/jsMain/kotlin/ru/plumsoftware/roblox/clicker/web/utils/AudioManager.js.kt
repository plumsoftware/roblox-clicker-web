@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.utils

import kotlinx.browser.document
import org.w3c.dom.Audio
import org.w3c.dom.events.Event

actual object AudioManager {

    private var backgroundMusic: Audio? = null

    // Флаг: играла ли музыка в момент сворачивания?
    // Нужно, чтобы не включать музыку, если пользователь сам нажал "Выкл звук"
    private var wasPlayingBeforeHide: Boolean = false

    actual var isSoundEnabled: Boolean = true
    actual var isMusicEnabled: Boolean = true

    init {
        // Подписываемся на событие браузера при первой инициализации объекта
        document.addEventListener("visibilitychange", {
            handleVisibilityChange()
        })
        console.log("[AudioManager] Visibility listener registered")
    }

    private fun handleVisibilityChange() {
        // Получаем состояние видимости (через dynamic, так надежнее в JS)
        val isHidden = document.asDynamic().hidden.unsafeCast<Boolean>()

        if (isHidden) {
            // --- ВКЛАДКА СВЕРНУТА ---
            console.log("[AudioManager] Tab hidden. Checking music...")

            // Если музыка есть и она НЕ на паузе
            if (backgroundMusic != null && backgroundMusic?.paused == false) {
                backgroundMusic?.pause()
                wasPlayingBeforeHide = true
                console.log("[AudioManager] Music paused (Auto)")
            } else {
                wasPlayingBeforeHide = false
            }
        } else {
            // --- ВКЛАДКА РАЗВЕРНУТА ---
            console.log("[AudioManager] Tab visible.")

            // Возобновляем, только если она играла до этого И настройки разрешают
            if (wasPlayingBeforeHide && isMusicEnabled && backgroundMusic != null) {
                backgroundMusic?.play()
                wasPlayingBeforeHide = false
                console.log("[AudioManager] Music resumed (Auto)")
            }
        }
    }

    actual fun playSound(fileName: String, volume: Double) {
        if (!isSoundEnabled) return

        // Не играем звуки, если вкладка свернута (экономим ресурсы)
        val isHidden = document.asDynamic().hidden.unsafeCast<Boolean>()
        if (isHidden) return

        try {
            val audio = Audio("sounds/$fileName")
            audio.volume = volume
            audio.play()
        } catch (e: dynamic) {
            console.error("Audio error:", e)
        }
    }

    actual fun playMusic(fileName: String, volume: Double) {
        if (!isMusicEnabled) return

        try {
            // Если уже играет этот же трек - выходим (чтобы не сбрасывать на начало)
            // Но проверяем, не на паузе ли он
            if (backgroundMusic?.src?.contains(fileName) == true) {
                if (backgroundMusic?.paused == true) {
                    backgroundMusic?.play()
                }
                return
            }

            stopMusic()

            val audio = Audio("sounds/$fileName")
            audio.volume = volume
            audio.loop = true

            // Сохраняем ссылку перед запуском
            backgroundMusic = audio

            // Запуск
            audio.play().catch { e ->
                console.warn("Autoplay blocked or error:", e)
            }

        } catch (e: dynamic) {
            console.error("Music error:", e)
        }
    }

    actual fun stopMusic() {
        backgroundMusic?.pause()
        backgroundMusic = null
        wasPlayingBeforeHide = false
    }
}