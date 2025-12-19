package ru.plumsoftware.roblox.clicker.web.ui.screens.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Вспомогательный компонент для пункта меню
@Composable
fun ShopMenuItem(
    text: String,
    onClick: () -> Unit
) {
    // Можно обернуть Text в Box, чтобы задать фон при желании,
    // но сейчас мы просто скругляем область клика на самом тексте.
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        color = Color.White,
        modifier = Modifier
            // 1. Сначала обрезаем форму (чтобы Ripple не выходил за углы)
            .clip(RoundedCornerShape(12.dp))
            // 2. Делаем кликабельным
            .clickable(onClick = onClick)
            // 3. Добавляем отступы ВНУТРИ кликабельной области, чтобы клик был красивее
            .padding(horizontal = 12.dp, vertical = 8.dp)
    )
}