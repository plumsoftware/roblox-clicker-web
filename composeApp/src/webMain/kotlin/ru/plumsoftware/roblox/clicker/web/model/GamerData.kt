package ru.plumsoftware.roblox.clicker.web.model

import kotlinx.serialization.Serializable

@Serializable // Эту аннотацию дает библиотека, она важна!
data class GamerData(
    val coins: Long = 0,
    val gems: Long = 0,

    // ID выбранных предметов (0 = дефолт)
    val selectedSkinId: Int = 1,
    val selectedMusicId: Int = 0,
    val selectedBackgroundId: Int = 0,
    val boostId: Int = 0,

    // Список ID купленных персонажей. По умолчанию только ID 1.
    val unlockedCharacterIds: List<Int> = listOf(1)
)