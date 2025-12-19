package ru.plumsoftware.roblox.clicker.web.ui.theme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Определяем формы для всего приложения
val AppShapes = Shapes(
    // Совсем мелкие элементы (теги, подсказки)
    // Делаем их почти квадратными, но не острыми
    extraSmall = RoundedCornerShape(4.dp),

    // Маленькие кнопки, текстовые поля
    // 8.dp - стандартный приятный радиус
    small = RoundedCornerShape(8.dp),

    // Карточки товаров, диалоговые окна (Cards)
    // 16.dp делает интерфейс более "пухлым" и игровым
    medium = RoundedCornerShape(16.dp),

    // Большие контейнеры, модальные окна (Modal Drawers)
    large = RoundedCornerShape(24.dp),

    // Очень большие поверхности или нижние шторки (Bottom Sheets)
    extraLarge = RoundedCornerShape(32.dp)
)