package ru.plumsoftware.roblox.clicker.web.model

import org.jetbrains.compose.resources.DrawableResource

data class Background(
    val id: Int,
    val name: String,
    val resourceName: DrawableResource,
    val price: Long,            // Цена покупки (гемы)

    // Изменяемые поля (var), так как мы будем их менять в процессе игры
    var isSelected: Boolean = false, // Выбран ли сейчас
    var isUnlocked: Boolean = false
)
