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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import roblox_clicker_web.composeapp.generated.resources.minecraft_forest_background

@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = koinViewModel()
    val state = viewModel.state.collectAsState()

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
                // Персонаж для клика
                // 1. Создаем источник взаимодействия (ты его уже создал, используем тот же)
                val characterInteractionSource = remember { MutableInteractionSource() }

                // 2. Отслеживаем состояние "Нажато" (true/false)
                val isPressed by characterInteractionSource.collectIsPressedAsState()

                // 3. Анимируем значение масштаба
                // Если нажато -> 0.9f (уменьшился), если нет -> 1.0f (нормальный)
                val scale by animateFloatAsState(
                    targetValue = if (isPressed) 0.9f else 1.0f,
                    // Настройка пружины для приятного "отскока"
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )

                Image(
                    painter = painterResource(Res.drawable.capitan_roblox_1),
                    contentDescription = null,
                    modifier = Modifier
                        // 4. Применяем масштаб ПЕРЕД clickable
                        .scale(scale)
                        .clickable(
                            interactionSource = characterInteractionSource,
                            indication = null, // Отключаем серую волну (ripple)
                            onClick = {

                            }
                        )
                )
            }

            // ---------------------------------------------------------
            // 2. ПРАВАЯ ЧАСТЬ (МАГАЗИН - 500dp)
            // ---------------------------------------------------------
            Column(
                modifier = Modifier
                    .width(500.dp)
                    .fillMaxHeight()
                    .background(Color.Black.copy(alpha = 0.4f)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Заголовок магазина
                Text(
                    modifier = Modifier.padding(vertical = 24.dp),
                    text = "магазин",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )

                // Разделяем область магазина на Меню (слева) и Список товаров (справа)
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start
                ) {

                    // --- КОЛОНКА НАВИГАЦИИ (МЕНЮ) ---
                    Column(
                        modifier = Modifier
                            .width(200.dp)
                            .fillMaxHeight()
                            .background(Color.Black.copy(alpha = 0.2f))
                            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        // Пункты меню
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

                    // --- КОЛОНКА КОНТЕНТА (СПИСОК ТОВАРОВ) ---
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
                            item {
                                Text(
                                    text = "Здесь будут товары...",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.LightGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
