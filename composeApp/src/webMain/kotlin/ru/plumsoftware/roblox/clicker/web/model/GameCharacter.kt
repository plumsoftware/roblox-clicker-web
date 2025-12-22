package ru.plumsoftware.roblox.clicker.web.model

import org.jetbrains.compose.resources.DrawableResource

data class GameCharacter(
    val id: Int,                // Уникальный ID (1, 2, 3...)
    val name: String,           // Название ("Нуб", "Капитан", "Хакер")
    val resourceName: DrawableResource,   // Имя файла картинки (для маппинга)
    val clickPower: Long,       // Сколько дает за клик (+1, +100...)
    val price: Long,            // Цена покупки

    // Изменяемые поля (var), так как мы будем их менять в процессе игры
    var isSelected: Boolean = false, // Выбран ли сейчас
    var isUnlocked: Boolean = false  // Куплен ли
)