package ru.plumsoftware.roblox.clicker.web.model

import kotlinx.serialization.Serializable

@Serializable
data class GamerData(
    val coins: Long = 0,
    val gems: Long = 0,

    // ID выбранных предметов (0 = дефолт)
    val selectedSkinId: Int = 1,
    val selectedMusicId: Int = 0,
    val boostId: Int = 0,

    // Список ID купленных персонажей. По умолчанию только ID 1.
    val unlockedCharacterIds: List<Int> = listOf(1),

    // Прогресс кликов для гемов
    val clickProgressForGems: Double = 0.0,

    // Накопленные гемы (которые лежат в карточке)
    val unclaimedGems: Long = 0,

    // --- ФОНЫ ---
    val selectedBackgroundId: Int = 1,          // ID 1 выбран по умолчанию
    val unlockedBackgroundIds: List<Int> = listOf(1), // ID 1 куплен сразу

    // Список купленных бустов
    val unlockedBoostIds: List<Int> = emptyList(),

    val isMusicOn: Boolean = true
)