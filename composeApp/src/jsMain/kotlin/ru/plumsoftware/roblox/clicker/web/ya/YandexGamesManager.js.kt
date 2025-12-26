@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.ya

import kotlin.js.Promise
import kotlinx.coroutines.await // Импорт для JS
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.plumsoftware.roblox.clicker.web.model.GamerData

// В JS промис возвращает обычный Boolean
external fun initYandexSdk(): Promise<Boolean>
external fun savePlayerData(json: String): Promise<Boolean>
external fun loadPlayerData(): Promise<String>
external fun gameReady()
external fun getLang(): String

actual object YandexGamesManager {
    actual var isInitialized: Boolean = false
        private set

    actual suspend fun init() {
        console.log("[JS Target] 🟢 init() -> Функция вызвана")
        try {
            console.log("[JS Target] 📞 init() -> Вызываем JS initYandexSdk()...")

            val success = initYandexSdk().await()

            console.log("[JS Target] 🔙 init() -> JS вернул результат:", success)

            if (success) {
                isInitialized = true
                console.log("[JS Target] ✅ init() -> Yandex SDK готов!")
            } else {
                console.warn("[JS Target] ⚠️ init() -> Yandex SDK вернул false")
            }
        } catch (e: dynamic) { // В JS ловим dynamic
            console.error("[JS Target] ❌ init() -> КРИТИЧЕСКАЯ ОШИБКА:", e)
        }
    }

    actual suspend fun saveGame(data: GamerData) {
        console.log("[JS Target] 💾 saveGame() -> Функция вызвана")

        if (!isInitialized) {
            console.warn("[JS Target] ⛔ saveGame() -> Отмена: SDK не готов")
            return
        }

        try {
            console.log("[JS Target] ⚙️ saveGame() -> Сериализуем данные...", data)
            val jsonString = Json.encodeToString(data)
            console.log("[JS Target] 📄 saveGame() -> JSON:", jsonString)

            console.log("[JS Target] 📞 saveGame() -> Отправляем в JS...")
            savePlayerData(jsonString).await()
            console.log("[JS Target] ✅ saveGame() -> Успешно!")

        } catch (e: dynamic) {
            console.error("[JS Target] ❌ saveGame() -> ОШИБКА:", e)
        }
    }

    actual suspend fun loadGame(): GamerData? {
        console.log("[JS Target] 📥 loadGame() -> Функция вызвана")

        if (!isInitialized) {
            console.warn("[JS Target] ⛔ loadGame() -> Отмена: SDK не готов")
            return null
        }

        return try {
            console.log("[JS Target] 📞 loadGame() -> Вызываем JS loadPlayerData()...")

            val jsonString = loadPlayerData().await()
            console.log("[JS Target] 🔙 loadGame() -> Получен JSON:", jsonString)

            if (jsonString.isEmpty() || jsonString == "{}") {
                console.log("[JS Target] ⚠️ loadGame() -> Пусто. Возвращаем null.")
                return null
            }

            console.log("[JS Target] ⚙️ loadGame() -> Парсим...")
            val jsonConfig = Json { ignoreUnknownKeys = true }
            val data = jsonConfig.decodeFromString<GamerData>(jsonString)

            console.log("[JS Target] ✅ loadGame() -> Данные:", data)
            return data

        } catch (e: dynamic) {
            console.error("[JS Target] ❌ loadGame() -> ОШИБКА:", e)
            null
        }
    }

    actual fun gameReady() {
        try {
            gameReady() // Вызов JS функции
        } catch (e: Throwable) {
            console.error("Game Ready Error", e)
        }
    }

    actual fun getLanguage(): String {
        return try {
            getLang()
        } catch (e: Throwable) {
            "ru"
        }
    }
}