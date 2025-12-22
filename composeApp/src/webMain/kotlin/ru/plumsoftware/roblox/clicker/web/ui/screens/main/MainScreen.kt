package ru.plumsoftware.roblox.clicker.web.ui.screens.main

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
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
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.components.CharacterItem
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.screens_dialogs.MainScreenScreens
import ru.plumsoftware.roblox.clicker.web.ui.theme.Fonts.getNumericFont
import ru.plumsoftware.roblox.clicker.web.utils.formatCompactNumber

@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

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

    Scaffold {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Res.drawable.minecraft_forest_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Row(modifier = Modifier.fillMaxSize()) {
            // ---------------------------------------------------------
            // 1. ЛЕВАЯ ЧАСТЬ (ИГРОВОЕ ПОЛЕ)
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
                                style = MaterialTheme.typography.displayMedium.copy(fontFamily = getNumericFont()),
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
                                style = MaterialTheme.typography.displayMedium.copy(fontFamily = getNumericFont()),
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
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                fillColor = Color.White,
                                outlineColor = Color.Black,
                                strokeWidth = 4f
                            )

                            OutlinedText(
                                text = formatCompactNumber(GameConfig.allCharacters.first { it.id == state.gamerData.selectedSkinId }.clickPower),
                                style = MaterialTheme.typography.displayMedium.copy(fontFamily = getNumericFont()),
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
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                fillColor = Color.White,
                                outlineColor = Color.Black,
                                strokeWidth = 4f
                            )

                            OutlinedText(
                                text = formatCompactNumber(state.gamerData.boostId),
                                style = MaterialTheme.typography.displayMedium.copy(fontFamily = getNumericFont()),
                                fontWeight = FontWeight.Bold,
                                fillColor = Color.White,
                                outlineColor = Color.Black,
                                strokeWidth = 4f
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
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Black,
                    fillColor = Color.White,
                    outlineColor = Color.Black,
                    strokeWidth = 6f // Заголовок жирнее
                )

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    // --- МЕНЮ ---
                    Column(
                        modifier = Modifier
                            .width(200.dp)
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
                        ShopMenuItem(text = "звуки") {
                            viewModel.onEvent(MainScreenPack.Event.onSoundsClick())
                        }
                        ShopMenuItem(text = "фоны") {
                            viewModel.onEvent(MainScreenPack.Event.onBackClick())
                        }
                    }

                    // --- КОНТЕНТ ---
                    // СПИСОК ТОВАРОВ
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
                            } else {
                                // Заглушка для других вкладок
                                item {
                                    Text("Этот раздел в разработке...", color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
