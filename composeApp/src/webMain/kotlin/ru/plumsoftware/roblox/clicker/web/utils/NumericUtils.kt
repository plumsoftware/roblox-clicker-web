package ru.plumsoftware.roblox.clicker.web.utils

import kotlin.math.floor

/**
 * Функция для сокращения больших чисел (для русской аудитории)
 * 1200 -> 1.2К
 * 1_500_000 -> 1.5М
 */
fun formatCompactNumber(value: Number): String {
    val doubleValue = value.toDouble()

    // 1. Если число меньше 1000, возвращаем как есть
    if (doubleValue < 1000) {
        return doubleValue.toInt().toString()
    }

    // 2. Определяем суффикс и делитель
    val (divider, suffix) = when {
        doubleValue >= 1_000_000_000_000_000 -> 1_000_000_000_000_000.0 to "КВ" // Квадриллион
        doubleValue >= 1_000_000_000_000 -> 1_000_000_000_000.0 to "Т"    // Триллион
        doubleValue >= 1_000_000_000 -> 1_000_000_000.0 to "Б"      // Миллиард
        doubleValue >= 1_000_000 -> 1_000_000.0 to "М"        // Миллион
        doubleValue >= 1_000 -> 1_000.0 to "К"          // Тысяча (Косарь)
        else -> 1.0 to ""
    }

    // 3. Делим и округляем до 1 знака после запятой
    // floor(x * 10) / 10 — это быстрый способ оставить 1 знак без округления вверх
    val formatted = floor((doubleValue / divider) * 10) / 10

    // 4. Убираем ".0", если число целое (например, не "5.0М", а "5М")
    return if (formatted % 1 == 0.0) {
        "${formatted.toInt()}$suffix"
    } else {
        "$formatted$suffix"
    }
}