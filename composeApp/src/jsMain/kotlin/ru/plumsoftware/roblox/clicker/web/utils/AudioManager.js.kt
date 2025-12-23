@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.utils

import kotlinx.browser.document
import org.w3c.dom.Audio

actual object AudioManager {

    private var backgroundMusic: Audio? = null

    // Флаг: играла ли музыка до сворачивания вкладки
    private var wasPlayingBeforeHide: Boolean = false

    // По умолчанию true, но ViewModel должна обновить это при загрузке профиля
    actual var isSoundEnabled: Boolean = true
    actual var isMusicEnabled: Boolean = true

    init {
        // Подписываемся на сворачивание вкладки
        document.addEventListener("visibilitychange", {
            handleVisibilityChange()
        })
        console.log("[Audio] Manager initialized")
    }

    private fun handleVisibilityChange() {
        val isHidden = document.asDynamic().hidden.unsafeCast<Boolean>()

        if (isHidden) {
            // Вкладку свернули -> Пауза
            if (backgroundMusic != null && backgroundMusic?.paused == false) {
                backgroundMusic?.pause()
                wasPlayingBeforeHide = true
                console.log("[Audio] Auto-paused (Tab hidden)")
            } else {
                wasPlayingBeforeHide = false
            }
        } else {
            // Вкладку открыли -> Возобновляем
            if (wasPlayingBeforeHide && isMusicEnabled && backgroundMusic != null) {
                val promise = backgroundMusic?.play()
                promise?.catch { e -> console.warn("[Audio] Resume blocked:", e) }
                wasPlayingBeforeHide = false
                console.log("[Audio] Auto-resumed (Tab visible)")
            }
        }
    }

    actual fun playSound(fileName: String, volume: Double) {
        if (!isSoundEnabled) {
            // console.log("[Audio] Skip sound (Disabled setting)")
            return
        }

        try {
            // Создаем новый объект на каждый клик для наложения звуков
            val audio = Audio("sounds/$fileName")
            audio.volume = volume
            val promise = audio.play()
            promise.catch { e ->
                // Ошибки клика можно не логировать громко, чтобы не спамить
            }
        } catch (e: dynamic) {
            console.error("[Audio] SFX Error:", e)
        }
    }

    actual fun playMusic(fileName: String, volume: Double) {
        // Даже если isMusicEnabled == false, мы сохраняем объект, но не играем,
        // или просто выходим. Лучше выходить.
        if (!isMusicEnabled) {
            console.log("[Audio] PlayMusic skipped (Music Disabled)")
            return
        }

        try {
            // Если уже играет ЭТОТ ЖЕ трек
            if (backgroundMusic?.src?.contains(fileName) == true) {
                if (backgroundMusic?.paused == true) {
                    console.log("[Audio] Resuming existing track")
                    backgroundMusic?.play()
                }
                return
            }

            stopMusic() // Останавливаем старый

            console.log("[Audio] Starting new track: $fileName")
            val audio = Audio("sounds/$fileName")
            audio.volume = volume
            audio.loop = true

            backgroundMusic = audio

            val promise = audio.play()
            promise.then {
                console.log("[Audio] Music started successfully")
            }.catch { e ->
                console.warn("[Audio] Autoplay blocked or file not found:", e)
            }

        } catch (e: dynamic) {
            console.error("[Audio] Music Error:", e)
        }
    }

    actual fun stopMusic() {
        if (backgroundMusic != null) {
            console.log("[Audio] Stopping music")
            backgroundMusic?.pause()
            backgroundMusic = null
        }
        wasPlayingBeforeHide = false
    }

    // Методы ручного управления (если понадобятся)
    actual fun pauseMusic() {
        backgroundMusic?.pause()
    }

    actual fun resumeMusic() {
        if (isMusicEnabled && backgroundMusic != null) {
            backgroundMusic?.play()
        }
    }
}