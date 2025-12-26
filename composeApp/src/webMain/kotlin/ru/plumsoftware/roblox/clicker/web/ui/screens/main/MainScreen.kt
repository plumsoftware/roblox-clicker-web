package ru.plumsoftware.roblox.clicker.web.ui.screens.main

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import roblox_clicker_web.composeapp.generated.resources.Res
import roblox_clicker_web.composeapp.generated.resources.background_icon
import roblox_clicker_web.composeapp.generated.resources.boost_icon
import roblox_clicker_web.composeapp.generated.resources.capitan_roblox_1
import roblox_clicker_web.composeapp.generated.resources.heroes_icon
import roblox_clicker_web.composeapp.generated.resources.sounds_icon
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.components.ShopMenuItem
import ru.plumsoftware.roblox.clicker.web.ui.theme.AppTheme

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import roblox_clicker_web.composeapp.generated.resources.coin_icon
import roblox_clicker_web.composeapp.generated.resources.gem_icon
import roblox_clicker_web.composeapp.generated.resources.homeless
import roblox_clicker_web.composeapp.generated.resources.minecraft_forest_background
import ru.plumsoftware.roblox.clicker.web.model.GameConfig
import ru.plumsoftware.roblox.clicker.web.ui.screens.components.OutlinedText
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.components.BackgroundCard
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.components.BoostCard
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.components.CharacterItem
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.screens_dialogs.MainScreenDialog
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.screens_dialogs.MainScreenScreens
import ru.plumsoftware.roblox.clicker.web.ui.theme.Fonts.getNumericFont
import ru.plumsoftware.roblox.clicker.web.utils.formatCompactNumber
import ru.plumsoftware.roblox.clicker.web.ya.YandexGamesManager

@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    val currentBackgroundResource = remember(state.gamerData.selectedBackgroundId) {
        state.backgroundsList.find { it.isSelected }?.resourceName
            ?: Res.drawable.minecraft_forest_background // Дефолт
    }

    // Логика поиска картинки выбранного персонажа
    val selectedCharacterResource = remember(state.gamerData.selectedSkinId) {
        state.charactersList.find { it.isSelected }?.resourceName
            ?: Res.drawable.homeless // Дефолт, если что-то пошло не так
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                MainScreenPack.Effect.onBackClicked -> {}
                MainScreenPack.Effect.onBoostClicked -> {}
                MainScreenPack.Effect.onHeroClicked -> {}
                MainScreenPack.Effect.onSettingsClicked -> {}
                MainScreenPack.Effect.onSoundsClicked -> {}
            }
        }
    }

    LaunchedEffect(Unit) {
        YandexGamesManager.gameReady()
    }

    Scaffold {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(currentBackgroundResource),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxSize()) {
                // ---------------------------------------------------------
                // 1. ЛЕВАЯ ЧАСТЬ
                // ---------------------------------------------------------
                Column(
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                    ) {
                        // --- ЗВУК ---
                        Card(
                            modifier = Modifier.align(Alignment.CenterStart),
                            onClick = { viewModel.onToggleMusic() },
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Black.copy(alpha = 0.4f)
                            ),
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(
                                2.dp,
                                Color.White.copy(alpha = 0.5f)
                            )
                        ) {
                            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                                OutlinedText(
                                    text = if (state.gamerData.isMusicOn) "звук: вкл" else "звук: выкл",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    fillColor = if (state.gamerData.isMusicOn) Color(0xFF00E676) else Color(
                                        0xFFFF1744
                                    ),
                                    outlineColor = Color.Black,
                                    strokeWidth = 3f
                                )
                            }
                        }

                        // --- КАРТОЧКА МОНЕТ ---
                        Card(
                            modifier = Modifier.align(Alignment.Center),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF9E79F)
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    modifier = Modifier.size(48.dp),
                                    painter = painterResource(Res.drawable.coin_icon),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = formatCompactNumber(state.gamerData.coins),
                                    style = MaterialTheme.typography.headlineSmall.copy(fontFamily = getNumericFont()),
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF7D6608),
                                )
                            }
                        }

                        // --- КАРТОЧКА ГЕМОВ ---
                        Card(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFB3E5FC)
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    modifier = Modifier.size(48.dp),
                                    painter = painterResource(Res.drawable.gem_icon),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = formatCompactNumber(state.gamerData.gems),
                                    style = MaterialTheme.typography.headlineSmall.copy(fontFamily = getNumericFont()),
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0277BD),
                                )
                            }
                        }
                    }

                    // Персонаж
                    val characterInteractionSource = remember { MutableInteractionSource() }
                    val isPressed by characterInteractionSource.collectIsPressedAsState()

                    val scale by animateFloatAsState(
                        targetValue = if (isPressed) 0.9f else 1.0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )

                    Image(
                        painter = painterResource(selectedCharacterResource),
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1.0f)
                            .scale(scale)
                            .clickable(
                                interactionSource = characterInteractionSource,
                                indication = null,
                                onClick = {
                                    viewModel.onMainCharacterClick()
                                }
                            )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp, end = 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Card(
                            modifier = Modifier,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Black.copy(alpha = 0.3f)
                            ),
                            shape = MaterialTheme.shapes.large.copy(
                                topStart = CornerSize(0.dp), bottomStart = CornerSize(0.dp)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(
                                    start = 40.dp,
                                    end = 20.dp,
                                    top = 10.dp,
                                    bottom = 10.dp
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = 20.dp,
                                    alignment = Alignment.CenterHorizontally
                                )
                            ) {
                                OutlinedText(
                                    text = "клик",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    fillColor = Color.White,
                                    outlineColor = Color.Black,
                                    strokeWidth = 4f
                                )

                                OutlinedText(
                                    text = formatCompactNumber(GameConfig.allCharacters.first { it.id == state.gamerData.selectedSkinId }.clickPower),
                                    style = MaterialTheme.typography.headlineMedium.copy(fontFamily = getNumericFont()),
                                    fontWeight = FontWeight.Bold,
                                    fillColor = Color.White,
                                    outlineColor = Color.Black,
                                    strokeWidth = 4f
                                )
                            }
                        }

                        Card(
                            modifier = Modifier,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Black.copy(alpha = 0.3f)
                            ),
                            shape = MaterialTheme.shapes.large.copy(
                                topEnd = CornerSize(0.dp), bottomEnd = CornerSize(0.dp)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    end = 40.dp,
                                    top = 10.dp,
                                    bottom = 10.dp
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = 20.dp,
                                    alignment = Alignment.CenterHorizontally
                                )
                            ) {
                                OutlinedText(
                                    text = "автоклик",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    fillColor = Color.White,
                                    outlineColor = Color.Black,
                                    strokeWidth = 4f
                                )

                                OutlinedText(
                                    text = formatCompactNumber(state.totalIncome),
                                    style = MaterialTheme.typography.headlineMedium.copy(fontFamily = getNumericFont()),
                                    fontWeight = FontWeight.Bold,
                                    fillColor = Color.White,
                                    outlineColor = Color.Black,
                                    strokeWidth = 4f
                                )
                            }
                        }
                    }

                    // Прогресс бар
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        val progressValue = if (state.maxClickProgressForGems > 0) {
                            (state.gamerData.clickProgressForGems / state.maxClickProgressForGems).toFloat()
                        } else {
                            0f
                        }
                        val animatedProgress by animateFloatAsState(
                            targetValue = progressValue,
                            animationSpec = tween(durationMillis = 100)
                        )

                        LinearProgressIndicator(
                            modifier = Modifier
                                .height(30.dp)
                                .clip(shape = MaterialTheme.shapes.medium)
                                .fillMaxWidth()
                                .weight(1.0f)
                                .border(1.dp, Color.Black, MaterialTheme.shapes.medium),
                            trackColor = Color(0xFFB3E5FC).copy(alpha = 0.5f),
                            color = Color(0xFF00B0FF),
                            gapSize = 0.dp,
                            progress = {
                                if (state.maxClickProgressForGems > 0)
                                    (state.gamerData.clickProgressForGems / state.maxClickProgressForGems).toFloat()
                                else 0f
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        val unclaimedCount = state.gamerData.unclaimedGems
                        Card(
                            modifier = Modifier,
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFB3E5FC)
                            ),
                            shape = MaterialTheme.shapes.medium,
                            onClick = {
                                if (unclaimedCount > 0) viewModel.onEvent(MainScreenPack.Event.onClaimGemsClick)
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                if (unclaimedCount > 0) {
                                    Text(
                                        text = "+$unclaimedCount",
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontFamily = getNumericFont()
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0277BD)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                }

                                Image(
                                    modifier = Modifier.size(38.dp).rotate(30f),
                                    painter = painterResource(Res.drawable.gem_icon),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                }

                // ---------------------------------------------------------
                // 2. ПРАВАЯ ЧАСТЬ (МАГАЗИН)
                // ---------------------------------------------------------
                Column(
                    modifier = Modifier
                        .width(570.dp)
                        .fillMaxHeight()
                        .background(Color.Black.copy(alpha = 0.4f)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Заголовок магазина
                    OutlinedText(
                        modifier = Modifier.padding(vertical = 24.dp),
                        text = "магазин",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        fillColor = Color.White,
                        outlineColor = Color.Black,
                        strokeWidth = 6f
                    )

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        // --- МЕНЮ ---
                        Column(
                            modifier = Modifier
                                .width(190.dp)
                                .fillMaxHeight()
                                .background(Color.Black.copy(alpha = 0.2f))
                                .padding(top = 20.dp, start = 16.dp, end = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            ShopMenuItem(text = "герои") {
                                viewModel.onEvent(MainScreenPack.Event.onHeroClick())
                            }
                            ShopMenuItem(text = "бусты") {
                                viewModel.onEvent(MainScreenPack.Event.onBoostClick())
                            }
                            ShopMenuItem(text = "фоны") {
                                viewModel.onEvent(MainScreenPack.Event.onBackClick())
                            }
                        }

                        // --- КОНТЕНТ ---
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LazyColumn(
                                contentPadding = PaddingValues(vertical = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                if (state.currentScreen is MainScreenScreens.Shop.HeroShop) {
                                    items(state.charactersList) { character ->
                                        CharacterItem(
                                            character = character,
                                            onClick = { viewModel.onShopItemClick(character) }
                                        )
                                    }
                                }
                                else if (state.currentScreen is MainScreenScreens.Shop.BackShop) {
                                    items(state.backgroundsList) { background ->
                                        BackgroundCard(
                                            background = background,
                                            onClick = { viewModel.onBackgroundItemClick(background) }
                                        )
                                    }
                                } else if (state.currentScreen is MainScreenScreens.Shop.BoostShop) {
                                    items(GameConfig.allBoosts) { boost ->
                                        BoostCard(
                                            boost = boost,
                                            isUnlocked = state.gamerData.unlockedBoostIds.contains(
                                                boost.id
                                            ),
                                            onClick = { viewModel.onBoostItemClick(boost) }
                                        )
                                    }
                                } else {
                                    item {
                                        Text("Этот раздел в разработке...", color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // --- ДИАЛОГОВОЕ ОКНО ---
            if (state.currentMainScreenDialog is MainScreenDialog.MainDialog.ClaimGemsDialog) {
                val gemsAmount =
                    (state.currentMainScreenDialog as MainScreenDialog.MainDialog.ClaimGemsDialog).amount

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .clickable(enabled = false) {},
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier.width(300.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = MaterialTheme.shapes.large,
                        border = BorderStroke(4.dp, Color.Black)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            OutlinedText(
                                text = "награда!",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                fillColor = Color(0xFFFFD600),
                                outlineColor = Color.Black
                            )

                            Image(
                                painter = painterResource(Res.drawable.gem_icon),
                                contentDescription = null,
                                modifier = Modifier.size(80.dp)
                            )

                            OutlinedText(
                                text = "+$gemsAmount ГЕМОВ",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                fillColor = Color(0xFF0277BD),
                                outlineColor = Color.Black
                            )

                            Button(
                                onClick = { viewModel.onEvent(MainScreenPack.Event.onCloseDialog) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF00E676
                                    )
                                ),
                                border = BorderStroke(0.dp, Color.Black),
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "забрать",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
