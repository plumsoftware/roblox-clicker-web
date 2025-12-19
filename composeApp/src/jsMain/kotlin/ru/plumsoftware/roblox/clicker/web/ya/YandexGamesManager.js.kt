@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.ya

import kotlin.js.Promise
import kotlinx.coroutines.await // Импорт для JS

// В JS промис возвращает обычный Boolean
external fun initYandexSdk(): Promise<Boolean>

actual object YandexGamesManager {
    actual var isInitialized: Boolean = false
        private set

    actual suspend fun init() {
        try {
            console.log("Kotlin (JS): Calling JS init...")

            // Ждем выполнения
            val success = initYandexSdk().await()

            if (success) {
                isInitialized = true
                console.log("Kotlin (JS): Yandex SDK is ready!")
            }
        } catch (e: dynamic) { // В JS ошибки динамические
            console.error("Kotlin (JS): Yandex Init Failed!", e)
        }
    }
}