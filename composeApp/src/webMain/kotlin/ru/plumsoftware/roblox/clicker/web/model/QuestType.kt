package ru.plumsoftware.roblox.clicker.web.model

import kotlinx.serialization.Serializable

enum class QuestType {
    CLICKS,         // Сделать клики
    EARN_COINS,     // Заработать монеты (кликом)
    BUY_ITEM,       // Купить что-то (героя, фон, буст)
    COLLECT_GEMS    // Собрать гемы (из шкалы)
}

@Serializable
data class Quest(
    val id: Int,
    val title: String,
    val type: QuestType,
    val target: Long,          // Сколько нужно сделать (например, 1000)
    val current: Long = 0,     // Сколько сделано
    val rewardCoins: Long = 0, // Награда
    val rewardGems: Long = 0,
    val isClaimed: Boolean = false
)