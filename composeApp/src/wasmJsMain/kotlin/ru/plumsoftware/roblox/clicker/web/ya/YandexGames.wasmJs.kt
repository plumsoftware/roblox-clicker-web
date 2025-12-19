@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.ya

import kotlin.js.Promise
import kotlin.js.JsBoolean
import kotlin.js.toBoolean
import kotlin.js.ExperimentalWasmJsInterop
import kotlinx.coroutines.await // Импорт для Wasm

// В Wasm промис обязан возвращать JsBoolean
@OptIn(ExperimentalWasmJsInterop::class)
external fun initYandexSdk(): Promise<JsBoolean>

actual object YandexGamesManager {
    actual var isInitialized: Boolean = false
        private set

    @OptIn(ExperimentalWasmJsInterop::class)
    actual suspend fun init() {
        try {
            println("Kotlin (Wasm): Calling JS init...")

            // await возвращает JsBoolean
            val resultJs = initYandexSdk().await<JsBoolean>()

            // Конвертируем в Kotlin Boolean
            if (resultJs.toBoolean()) {
                isInitialized = true
                println("Kotlin (Wasm): Yandex SDK is ready!")
            }
        } catch (e: Throwable) { // В Wasm ловим Throwable
            println("Kotlin (Wasm): Yandex Init Failed! Error: $e")
        }
    }
}