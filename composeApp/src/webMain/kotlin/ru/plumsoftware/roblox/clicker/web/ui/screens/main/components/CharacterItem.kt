package ru.plumsoftware.roblox.clicker.web.ui.screens.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import roblox_clicker_web.composeapp.generated.resources.Res
import roblox_clicker_web.composeapp.generated.resources.coin_icon
import ru.plumsoftware.roblox.clicker.web.model.GameCharacter
import ru.plumsoftware.roblox.clicker.web.ui.theme.Fonts
import ru.plumsoftware.roblox.clicker.web.utils.formatCompactNumber

@Composable
fun CharacterItem(
    character: GameCharacter,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        character.isSelected -> Color(0xFF00E676).copy(alpha = 0.5f) // Зеленый (Выбран)
        character.isUnlocked -> Color.White.copy(alpha = 0.2f)       // Обычный (Куплен)
        else -> Color.Black.copy(alpha = 0.3f)                       // Темный (Закрыт)
    }

    val borderColor = if (character.isSelected) Color(0xFF00E676) else Color.Transparent

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(2.dp, borderColor),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(character.resourceName),
                    contentDescription = null,
                    modifier = Modifier.size(130.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "+${formatCompactNumber(character.clickPower)} клик",
                style = MaterialTheme.typography.titleLarge.copy(fontFamily = Fonts.getNumericFont()),
                color = Color.Green,
                fontWeight = FontWeight.Medium,
            )

            // Цена или Статус
            if (character.isUnlocked) {
                if (character.isSelected) {
                    Text("выбран", color = Color.Green, fontWeight = FontWeight.Medium)
                } else {
                    Text("куплено", color = Color.Gray, fontWeight = FontWeight.Medium)
                }
            } else {
                // Цена
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = formatCompactNumber(character.price),
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = Fonts.getNumericFont()),
                        color = Color(0xFFFFD600), // Золотой
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(
                        painter = painterResource(Res.drawable.coin_icon),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}