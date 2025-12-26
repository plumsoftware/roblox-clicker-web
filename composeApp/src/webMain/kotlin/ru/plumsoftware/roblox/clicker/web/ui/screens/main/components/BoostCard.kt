package ru.plumsoftware.roblox.clicker.web.ui.screens.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import roblox_clicker_web.composeapp.generated.resources.Res
import roblox_clicker_web.composeapp.generated.resources.coin_icon
import roblox_clicker_web.composeapp.generated.resources.gem_icon
import ru.plumsoftware.roblox.clicker.web.model.GameBoost
import ru.plumsoftware.roblox.clicker.web.ui.theme.Fonts
import ru.plumsoftware.roblox.clicker.web.utils.formatCompactNumber

@Composable
fun BoostCard(
    boost: GameBoost,
    isUnlocked: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        if (isUnlocked) Color(0xFF00E676).copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.3f)
    val borderColor = if (isUnlocked) Color(0xFF00E676) else Color.Gray

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(2.dp, borderColor),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // ВЕРХНЯЯ ЧАСТЬ: Иконка + Имя + Доход
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(boost.resourceName),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = boost.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Text(
                        text = "+${formatCompactNumber(boost.income)} / сек",
                        style = MaterialTheme.typography.titleMedium.copy(fontFamily = Fonts.getNumericFont()),
                        color = Color(0xFF00E676)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // НИЖНЯЯ ЧАСТЬ: Цена (справа) или Статус
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (isUnlocked) {
                    Text("куплено", color = Color.Green, fontWeight = FontWeight.Medium)
                } else {
                    // Группируем цены в ряд (если влезают) или в колонку
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (boost.priceCoins > 0) {
                            Text(
                                text = formatCompactNumber(boost.priceCoins),
                                color = Color(0xFFFFD600),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium.copy(fontFamily = Fonts.getNumericFont())
                            )
                            Image(
                                painter = painterResource(Res.drawable.coin_icon),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        if (boost.priceCoins > 0 && boost.priceGems > 0) {
                            Spacer(modifier = Modifier.width(12.dp))
                        }

                        if (boost.priceGems > 0) {
                            Text(
                                text = formatCompactNumber(boost.priceGems),
                                color = Color(0xFF00B0FF),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium.copy(fontFamily = Fonts.getNumericFont())
                            )
                            Image(
                                painter = painterResource(Res.drawable.gem_icon),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}