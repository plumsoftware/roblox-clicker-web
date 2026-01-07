package ru.plumsoftware.roblox.clicker.web.utils

import kotlin.js.Date

actual object DateHelper {
    actual fun getTodayDate(): String {
        // toDateString() возвращает только дату без времени (напр. "Tue Jan 06 2026")
        return Date().toDateString()
    }
}