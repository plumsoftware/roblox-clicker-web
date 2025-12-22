@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.ya

import kotlinx.serialization.json.Json
import kotlinx.coroutines.await
import ru.plumsoftware.roblox.clicker.web.model.GamerData
import kotlin.js.Promise
import kotlin.js.JsAny
import kotlin.js.JsBoolean
import kotlin.js.JsString
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.toBoolean

@OptIn(ExperimentalWasmJsInterop::class)
external fun initYandexSdk(): Promise<JsAny?>

@OptIn(ExperimentalWasmJsInterop::class)
external fun savePlayerData(json: String): Promise<JsAny?>

@OptIn(ExperimentalWasmJsInterop::class)
external fun loadPlayerData(): Promise<JsAny?>

actual object YandexGamesManager {
    actual var isInitialized: Boolean = false
        private set

    @OptIn(ExperimentalWasmJsInterop::class)
    actual suspend fun init() {
        println("[Wasm Target] 🟢 init() -> Функция вызвана")
        try {
            println("[Wasm Target] 📞 init() -> Вызываем JS...")

            val resultAny: JsAny? = initYandexSdk().await()

            // В Wasm приводим типы аккуратно
            // Если JS вернул null/undefined, resultAny будет null
            val resultJs = resultAny?.unsafeCast<JsBoolean>()
            val success = resultJs?.toBoolean() ?: false

            println("[Wasm Target] 🔙 init() -> Результат: $success")

            if (success) {
                isInitialized = true
                println("[Wasm Target] ✅ Yandex SDK готов!")
            } else {
                println("[Wasm Target] ⚠️ Yandex SDK вернул false")
            }
        } catch (e: Throwable) {
            println("[Wasm Target] ❌ ОШИБКА: $e")
        }
    }

    @OptIn(ExperimentalWasmJsInterop::class)
    actual suspend fun saveGame(data: GamerData) {
        println("[Wasm Target] 💾 saveGame() -> Старт")

        if (!isInitialized) return

        try {
            val jsonString = Json.encodeToString(data)
            println("[Wasm Target] 📄 JSON: $jsonString")

            // ИСПРАВЛЕНИЕ ОШИБКИ ЗДЕСЬ:
            // Мы явно указываем тип переменной, чтобы await() понял, чего мы от него хотим.
            val unused: JsAny? = savePlayerData(jsonString).await()

            println("[Wasm Target] ✅ Сохранено!")
        } catch (e: Throwable) {
            println("[Wasm Target] ❌ ОШИБКА: $e")
        }
    }

    @OptIn(ExperimentalWasmJsInterop::class)
    actual suspend fun loadGame(): GamerData? {
        println("[Wasm Target] 📥 loadGame() -> Старт")

        if (!isInitialized) return null

        return try {
            // Тут ошибки не было, потому что ты присваивал результат в resultAny
            val resultAny: JsAny? = loadPlayerData().await()

            // Проверка на null
            if (resultAny == null) return null

            val jsonString = resultAny.unsafeCast<JsString>().toString()

            println("[Wasm Target] 🔙 JSON: $jsonString")

            if (jsonString.isEmpty() || jsonString == "{}") return null

            val jsonConfig = Json { ignoreUnknownKeys = true }
            jsonConfig.decodeFromString<GamerData>(jsonString)
        } catch (e: Throwable) {
            println("[Wasm Target] ❌ ОШИБКА: $e")
            null
        }
    }
}