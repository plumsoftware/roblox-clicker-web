package ru.plumsoftware.roblox.clicker.web.ui.screens.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import roblox_clicker_web.composeapp.generated.resources.Res
import roblox_clicker_web.composeapp.generated.resources.coin_icon
import roblox_clicker_web.composeapp.generated.resources.gem_icon
import ru.plumsoftware.roblox.clicker.web.model.Quest
import ru.plumsoftware.roblox.clicker.web.ui.theme.Fonts
import ru.plumsoftware.roblox.clicker.web.utils.formatCompactNumber

@Composable
fun QuestItem(
    quest: Quest,
    onClaim: () -> Unit
) {
    val isCompleted = quest.current >= quest.target
    val progress = (quest.current.toFloat() / quest.target.toFloat()).coerceIn(0f, 1f)

    Card(
        modifier = Modifier.fillMaxWidth().height(80.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f)),
        border = BorderStroke(2.dp, if (isCompleted && !quest.isClaimed) Color(0xFF00E676) else Color.Gray),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Инфо о задании
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = quest.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Прогресс бар
                Box(modifier = Modifier.fillMaxWidth(0.8f).height(12.dp)) {
                    // Фон
                    Box(modifier = Modifier.fillMaxSize().background(Color.Gray, RoundedCornerShape(4.dp)))
                    // Заполнение
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .fillMaxHeight()
                            .background(Color(0xFF00B0FF), RoundedCornerShape(4.dp))
                    )
                    // Текст
                    Text(
                        text = "${formatCompactNumber(quest.current)} / ${formatCompactNumber(quest.target)}",
                        style = MaterialTheme.typography.labelSmall.copy(fontFamily = Fonts.getNumericFont()),
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Награда и Кнопка
            Column(horizontalAlignment = Alignment.End) {
                // Отображение награды
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (quest.rewardGems > 0) {
                        Text("+${quest.rewardGems}", color = Color(0xFF00B0FF), fontWeight = FontWeight.Bold)
                        Image(painterResource(Res.drawable.gem_icon), null, Modifier.size(20.dp))
                    } else {
                        Text("+${formatCompactNumber(quest.rewardCoins)}", color = Color(0xFFFFD600), fontWeight = FontWeight.Bold)
                        Image(painterResource(Res.drawable.coin_icon), null, Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                if (quest.isClaimed) {
                    Text("выполнено", color = Color.Gray, fontWeight = FontWeight.Bold)
                } else {
                    Button(
                        onClick = onClaim,
                        enabled = isCompleted,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00E676),
                            disabledContainerColor = Color.Gray
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                        modifier = Modifier.height(32.dp)
                    ) { Text("забрать", fontSize = 12.sp, color = Color.White) }
                }
            }
        }
    }
}