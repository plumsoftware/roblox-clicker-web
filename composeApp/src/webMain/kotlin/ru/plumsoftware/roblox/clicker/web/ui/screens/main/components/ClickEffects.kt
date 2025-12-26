package ru.plumsoftware.roblox.clicker.web.ui.screens.main.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.math.PI
import androidx.compose.runtime.withFrameNanos
import ru.plumsoftware.roblox.clicker.web.ui.theme.Fonts

// Модель одной частицы
data class Particle(
    val id: Long,
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    var alpha: Float = 1f,
    var scale: Float = 1f,
    var rotation: Float = 0f,
    var rotationSpeed: Float = 0f,
    val type: ParticleType,
    val text: String = ""
)

enum class ParticleType { COIN, TEXT }

class ClickEffectsState {
    val particles = mutableStateListOf<Particle>()
    private var idCounter = 0L

    fun addExplosion(position: Offset, amountText: String) {
        // Уменьшили количество монеток (было 6, стало 3)
        val count = 3

        // 1. ТЕКСТ (Цифра урона)
        particles.add(
            Particle(
                id = idCounter++,
                x = position.x,
                y = position.y - 40f, // Чуть выше пальца
                vx = 0f,
                vy = -2f, // Медленно плывет вверх
                type = ParticleType.TEXT,
                text = "+$amountText",
                scale = 1f // Нормальный размер текста
            )
        )

        // 2. МОНЕТКИ (Аккуратный фонтанчик)
        repeat(count) {
            particles.add(
                Particle(
                    id = idCounter++,
                    x = position.x,
                    y = position.y,
                    // Разлет по горизонтали очень маленький (от -3 до 3)
                    vx = (Random.nextFloat() - 0.5f) * 6f,
                    // Небольшой подпрыг вверх (от -5 до -10)
                    vy = -Random.nextFloat() * 5f - 5f,
                    rotation = Random.nextFloat() * 360f,
                    rotationSpeed = (Random.nextFloat() - 0.5f) * 10f,
                    // Монетки маленькие (30-50% от оригинала)
                    scale = Random.nextFloat() * 0.2f + 0.3f,
                    type = ParticleType.COIN
                )
            )
        }
    }

    fun update() {
        val iterator = particles.iterator()
        while (iterator.hasNext()) {
            val p = iterator.next()

            p.x += p.vx
            p.y += p.vy

            if (p.type == ParticleType.COIN) {
                p.vy += 0.5f // Гравитация (падают вниз)
                p.rotation += p.rotationSpeed
                p.alpha -= 0.03f // Исчезают довольно быстро
            } else {
                // Текст исчезает медленнее
                p.alpha -= 0.02f
            }

            if (p.alpha <= 0f) {
                iterator.remove()
            }
        }
    }
}

@Composable
fun rememberClickEffectsState(): ClickEffectsState {
    return remember { ClickEffectsState() }
}

@Composable
fun ClickEffectsCanvas(
    state: ClickEffectsState,
    modifier: Modifier = Modifier,
    coinPainter: Painter,
    textMeasurer: TextMeasurer
) {
    val fontFamily = Fonts.getNumericFont()
    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos {
                state.update()
            }
        }
    }

    Canvas(modifier = modifier) {
        state.particles.forEach { p ->
            if (p.alpha > 0f) {
                when (p.type) {
                    ParticleType.COIN -> {
                        withTransform({
                            translate(p.x, p.y)
                            rotate(p.rotation)
                            scale(p.scale, p.scale)
                        }) {
                            with(coinPainter) {
                                translate(-intrinsicSize.width / 2, -intrinsicSize.height / 2) {
                                    draw(size = intrinsicSize, alpha = p.alpha)
                                }
                            }
                        }
                    }
                    ParticleType.TEXT -> {
                        // Текст поменьше и аккуратнее
                        val fontSize = 64.sp

                        val textStyle = TextStyle(
                            fontSize = fontSize,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00E676) // Ярко-зеленый
                        )
                        val outlineStyle = TextStyle(
                            fontSize = fontSize,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        withTransform({
                            translate(p.x, p.y)
                            // Текст тоже может слегка увеличиваться при появлении
                            // scale(p.scale, p.scale)
                        }) {
                            // Обводка
                            drawText(textMeasurer, p.text, style = outlineStyle, topLeft = Offset(1f, 1f))
                            drawText(textMeasurer, p.text, style = outlineStyle, topLeft = Offset(-1f, -1f))
                            // Основной цвет
                            drawText(textMeasurer, p.text, style = textStyle)
                        }
                    }
                }
            }
        }
    }
}