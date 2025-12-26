@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.utils

import kotlinx.browser.document
import org.w3c.dom.Audio
import kotlinx.browser.window

actual object AudioManager {

    // --- WEB AUDIO API (Для музыки) ---
    private var audioContext: dynamic = null
    private var musicSource: dynamic = null
    private var musicGainNode: dynamic = null // Для громкости
    private var currentMusicUrl: String? = null

    // --- HTML AUDIO (Для звуков) ---
    // Для кликов оставляем старый способ, это ок

    private var wasPlayingBeforeHide: Boolean = false

    actual var isSoundEnabled: Boolean = true
    actual var isMusicEnabled: Boolean = true

    init {
        // Инициализируем AudioContext
        // Делаем это лениво или при первом клике, так как браузеры требуют жеста
        try {
            val ctxConstructor = js("window.AudioContext || window.webkitAudioContext")
            audioContext = js("new ctxConstructor()")
        } catch (e: dynamic) {
            console.error("[AudioManager] Web Audio API not supported", e)
        }

        // Слушаем сворачивание
        document.addEventListener("visibilitychange", {
            handleVisibilityChange()
        })
    }

    private fun handleVisibilityChange() {
        val isHidden = document.asDynamic().hidden.unsafeCast<Boolean>()

        if (isHidden) {
            // --- ВКЛАДКА СВЕРНУТА ---
            if (audioContext != null && audioContext.state == "running") {
                // Приостанавливаем весь контекст (музыку)
                audioContext.suspend()
                wasPlayingBeforeHide = true
                console.log("[AudioManager] Context suspended (Tab hidden)")
            } else {
                wasPlayingBeforeHide = false
            }
        } else {
            // --- ВКЛАДКА РАЗВЕРНУТА ---
            if (wasPlayingBeforeHide && isMusicEnabled) {
                // Возобновляем
                audioContext?.resume()
                wasPlayingBeforeHide = false
                console.log("[AudioManager] Context resumed (Tab visible)")
            }
        }
    }

    actual fun playSound(fileName: String, volume: Double) {
        if (!isSoundEnabled) return
        val isHidden = document.asDynamic().hidden.unsafeCast<Boolean>()
        if (isHidden) return

        try {
            // Для коротких звуков оставляем new Audio(), это не вызывает плеер
            val audio = Audio("sounds/$fileName")
            audio.volume = volume
            val promise = audio.play()
            promise.catch { }
        } catch (e: dynamic) {
            console.error("SFX error:", e)
        }
    }

    actual fun playMusic(fileName: String, volume: Double) {
        if (!isMusicEnabled) return
        if (audioContext == null) return

        // Если уже играет этот трек — просто убеждаемся, что контекст работает
        if (currentMusicUrl == fileName && musicSource != null) {
            if (audioContext.state == "suspended") {
                audioContext.resume()
            }
            return
        }

        stopMusic() // Останавливаем текущий

        currentMusicUrl = fileName
        val path = "sounds/$fileName"

        // Загружаем файл через fetch и декодируем
        window.fetch(path).then { response ->
            response.arrayBuffer().then { buffer ->
                audioContext.decodeAudioData(buffer, { decodedData ->
                    playDecodedMusic(decodedData, volume)
                }, { e ->
                    console.error("[AudioManager] Decode error:", e)
                })
            }
        }.catch { e ->
            console.error("[AudioManager] Fetch error:", e)
        }
    }

    private fun playDecodedMusic(buffer: dynamic, volume: Double) {
        try {
            // 1. Создаем источник
            musicSource = audioContext.createBufferSource()
            musicSource.buffer = buffer
            musicSource.loop = true // Зацикливаем

            // 2. Создаем узел громкости
            musicGainNode = audioContext.createGain()
            musicGainNode.gain.value = volume

            // 3. Соединяем: Source -> Gain -> Destination (динамики)
            musicSource.connect(musicGainNode)
            musicGainNode.connect(audioContext.destination)

            // 4. Запускаем
            musicSource.start(0)
            console.log("[AudioManager] Web Audio Music Started")

            // Если контекст был на паузе (браузер заблокировал автоплей), пробуем возобновить
            if (audioContext.state == "suspended") {
                audioContext.resume()
            }

        } catch (e: dynamic) {
            console.error("[AudioManager] Play decoded error:", e)
        }
    }

    actual fun stopMusic() {
        try {
            if (musicSource != null) {
                musicSource.stop()
                musicSource.disconnect()
                musicSource = null
            }
            currentMusicUrl = null
        } catch (e: dynamic) {
            console.warn("Stop error", e)
        }
    }

    actual fun pauseMusic() {
        // При ручной паузе (не сворачивание) просто саспендим контекст
        audioContext?.suspend()
    }

    actual fun resumeMusic() {
        if (isMusicEnabled) {
            audioContext?.resume()
        }
    }
}