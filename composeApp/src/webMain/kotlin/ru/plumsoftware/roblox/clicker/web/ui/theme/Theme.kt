package ru.plumsoftware.roblox.clicker.web.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Настраиваем ТОЛЬКО светлую тему, так как игра должна быть яркой
private val BrightLightColors = lightColorScheme(
    // --- PRIMARY (Желтые кнопки) ---
    primary = RobYellow,
    onPrimary = Color.Black, // Черный текст на желтом читается лучше всего
    primaryContainer = RobYellowDark,
    onPrimaryContainer = Color.Black,

    // --- SECONDARY (Синие акценты/Хедеры) ---
    secondary = RobBlue,
    onSecondary = Color.White, // Белый текст на синем
    secondaryContainer = RobBlueDark,
    onSecondaryContainer = Color.White,

    // --- TERTIARY (Премиум/Гемы) ---
    tertiary = RobPink,
    onTertiary = Color.White,
    tertiaryContainer = RobPinkLight,
    onTertiaryContainer = Color.White,

    // --- ФОН И ПОВЕРХНОСТИ ---
    background = BrightBackground, // Светло-серый фон экрана
    onBackground = InkBlack,       // Черный текст

    surface = PureWhite,           // Белые карточки товаров
    onSurface = InkBlack,
    surfaceVariant = Color(0xFFE0E0E0), // Цвет рамок/разделителей
    onSurfaceVariant = Color.Gray,

    // --- ОШИБКИ И СТАТУСЫ ---
    error = RobRed,
    onError = Color.White
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BrightLightColors,
        shapes = AppShapes,
        typography = AppTypography.getTypography(),
        content = content
    )
}