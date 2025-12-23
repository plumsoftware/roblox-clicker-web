package ru.plumsoftware.roblox.clicker.web.model

import org.jetbrains.compose.resources.DrawableResource

data class GameBoost(
    val id: Int,
    val name: String,
    val resourceName: DrawableResource, // Иконка
    val income: Long,       // Доход в секунду (CPS)

    // Цены (если 0 - значит бесплатно/не требует этой валюты)
    val priceCoins: Long = 0,
    val priceGems: Long = 0
)