package ru.plumsoftware.roblox.clicker.web

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform