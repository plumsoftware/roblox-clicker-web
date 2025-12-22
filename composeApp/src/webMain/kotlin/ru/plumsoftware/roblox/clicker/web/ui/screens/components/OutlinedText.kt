package ru.plumsoftware.roblox.clicker.web.ui.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun OutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontWeight: FontWeight? = null,
    fillColor: Color = Color.White, // Цвет внутри букв
    outlineColor: Color = Color.Black, // Цвет обводки
    strokeWidth: Float = 3f, // Толщина обводки
    textAlign: TextAlign? = null
) {
    val mergedStyle = style.merge(TextStyle(fontWeight = fontWeight))

    Box(modifier = modifier) {
        // Слой 1: Обводка (рисуется позади)
        Text(
            text = text,
            textAlign = textAlign,
            style = mergedStyle.merge(
                TextStyle(
                    color = outlineColor,
                    drawStyle = Stroke(
                        width = strokeWidth,
                        miter = 10f,
                        join = StrokeJoin.Round
                    )
                )
            )
        )
        // Слой 2: Заливка (рисуется сверху)
        Text(
            text = text,
            textAlign = textAlign,
            style = mergedStyle.merge(TextStyle(color = fillColor))
        )
    }
}