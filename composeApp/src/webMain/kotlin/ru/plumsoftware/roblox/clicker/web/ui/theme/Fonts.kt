package ru.plumsoftware.roblox.clicker.web.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import roblox_clicker_web.composeapp.generated.resources.Res
import roblox_clicker_web.composeapp.generated.resources.nougat_extra_black
import roblox_clicker_web.composeapp.generated.resources.oofroblox

object Fonts {
    @Composable
    fun getFont() = FontFamily(
        Font(
            resource = Res.font.oofroblox,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.oofroblox,
            weight = FontWeight.Medium
        ),
        Font(
            resource = Res.font.oofroblox,
            weight = FontWeight.SemiBold
        ),
        Font(
            resource = Res.font.oofroblox,
            weight = FontWeight.Bold
        ),
        Font(
            resource = Res.font.oofroblox,
            weight = FontWeight.Black
        )
    )

    @Composable
    fun getNumericFont() = FontFamily(
        Font(
            resource = Res.font.nougat_extra_black,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.nougat_extra_black,
            weight = FontWeight.Medium
        ),
        Font(
            resource = Res.font.nougat_extra_black,
            weight = FontWeight.SemiBold
        ),
        Font(
            resource = Res.font.nougat_extra_black,
            weight = FontWeight.Bold
        ),
        Font(
            resource = Res.font.nougat_extra_black,
            weight = FontWeight.Black
        )
    )
}