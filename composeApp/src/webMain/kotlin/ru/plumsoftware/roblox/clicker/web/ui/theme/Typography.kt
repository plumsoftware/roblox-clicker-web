package ru.plumsoftware.roblox.clicker.web.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTypography {

    @Composable
    fun getTypography(): Typography {
        // 1. Получаем твой шрифт (RobloxFont)
        val robloxFontFamily = Fonts.getFont()

        // 2. Возвращаем настроенный класс Typography
        return Typography(
            // --- DISPLAY (Огромные заголовки, например, для сплэш-экрана или "Victory!") ---
            displayLarge = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Black, // Самый жирный
                fontSize = 57.sp,
                lineHeight = 64.sp,
                letterSpacing = (-0.25).sp
            ),
            displayMedium = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp,
                lineHeight = 52.sp,
                letterSpacing = 7.sp
            ),
            displaySmall = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                lineHeight = 44.sp,
                letterSpacing = 7.sp
            ),

            // --- HEADLINE (Заголовки экранов, название магазина) ---
            headlineLarge = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 40.sp,
                letterSpacing = 5.sp
            ),
            headlineMedium = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                letterSpacing = 5.sp
            ),
            headlineSmall = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                letterSpacing = 5.sp
            ),

            // --- TITLE (Подзаголовки в списках, имена предметов) ---
            titleLarge = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 5.sp
            ),
            titleMedium = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
            ),
            titleSmall = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp
            ),

            // --- BODY (Основной текст, описания предметов) ---
            bodyLarge = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
            ),
            bodyMedium = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp
            ),
            bodySmall = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.4.sp
            ),

            // --- LABEL (Текст на кнопках, мелкие подписи) ---
            labelLarge = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Bold, // На кнопках лучше жирный
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp
            ),
            labelMedium = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp
            ),
            labelSmall = TextStyle(
                fontFamily = robloxFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp
            )
        )
    }
}