@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.plumsoftware.roblox.clicker.web.utils

expect object DateHelper {
    /**
     * Возвращает текущую дату в формате строки (например, "Tue Jan 06 2026")
     */
    fun getTodayDate(): String
}