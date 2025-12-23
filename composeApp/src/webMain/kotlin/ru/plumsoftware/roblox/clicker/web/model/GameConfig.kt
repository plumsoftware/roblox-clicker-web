package ru.plumsoftware.roblox.clicker.web.model

import roblox_clicker_web.composeapp.generated.resources.Res
import roblox_clicker_web.composeapp.generated.resources.amethysto
import roblox_clicker_web.composeapp.generated.resources.apple_valacas
import roblox_clicker_web.composeapp.generated.resources.atomic_waste
import roblox_clicker_web.composeapp.generated.resources.back_3
import roblox_clicker_web.composeapp.generated.resources.back_9
import roblox_clicker_web.composeapp.generated.resources.bird
import roblox_clicker_web.composeapp.generated.resources.bombardiro_crocadilo
import roblox_clicker_web.composeapp.generated.resources.brawl_starts_background
import roblox_clicker_web.composeapp.generated.resources.builder_1
import roblox_clicker_web.composeapp.generated.resources.capitan_roblox_1
import roblox_clicker_web.composeapp.generated.resources.cosminus_5
import roblox_clicker_web.composeapp.generated.resources.darkmatter
import roblox_clicker_web.composeapp.generated.resources.flex
import roblox_clicker_web.composeapp.generated.resources.homeless
import roblox_clicker_web.composeapp.generated.resources.minecraft_forest_background
import roblox_clicker_web.composeapp.generated.resources.monster
import roblox_clicker_web.composeapp.generated.resources.neon_overdrive_roblox_11
import roblox_clicker_web.composeapp.generated.resources.ninja
import roblox_clicker_web.composeapp.generated.resources.ninja_smoking
import roblox_clicker_web.composeapp.generated.resources.roblox_background
import roblox_clicker_web.composeapp.generated.resources.roblox_castle_background
import roblox_clicker_web.composeapp.generated.resources.roblox_doors_background
import roblox_clicker_web.composeapp.generated.resources.secret_roblox_7
import roblox_clicker_web.composeapp.generated.resources.shrecus
import roblox_clicker_web.composeapp.generated.resources.skuf_shek
import roblox_clicker_web.composeapp.generated.resources.spider
import roblox_clicker_web.composeapp.generated.resources.squid_game_guardian
import roblox_clicker_web.composeapp.generated.resources.super_roblox_6
import roblox_clicker_web.composeapp.generated.resources.tigris_roblox_10
import roblox_clicker_web.composeapp.generated.resources.tualet_shreka_background

object GameConfig {
    val allCharacters = listOf(
        // --- УРОВЕНЬ 1: НАЧАЛО ---
        GameCharacter(
            id = 1,
            name = "Боевой Бомжик",
            resourceName = Res.drawable.homeless,
            clickPower = 1,
            price = 0,
            isSelected = true,
            isUnlocked = true
        ),
        GameCharacter(
            id = 2,
            name = "Строитель Михалыч",
            resourceName = Res.drawable.builder_1,
            clickPower = 2,
            price = 300, // Было 100
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 3,
            name = "Злой Чирик",
            resourceName = Res.drawable.bird,
            clickPower = 4,
            price = 1_500, // Было 500
            isSelected = false,
            isUnlocked = false
        ),

        // --- УРОВЕНЬ 2: МЕМЫ ---
        GameCharacter(
            id = 4,
            name = "Яблочный Валакас",
            resourceName = Res.drawable.apple_valacas,
            clickPower = 8,
            price = 4_500, // Было 1,500
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 5,
            name = "Скуф Обыкновенный",
            resourceName = Res.drawable.skuf_shek,
            clickPower = 15,
            price = 9_000, // Было 3,000
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 6,
            name = "Шрекус Болотный",
            resourceName = Res.drawable.shrecus,
            clickPower = 25,
            price = 22_500, // Было 7,500
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 7,
            name = "Человек-Тапок",
            resourceName = Res.drawable.spider,
            clickPower = 40,
            price = 45_000, // Было 15,000
            isSelected = false,
            isUnlocked = false
        ),

        // --- УРОВЕНЬ 3: ОПАСНЫЕ ---
        GameCharacter(
            id = 8,
            name = "Ниндзя",
            resourceName = Res.drawable.ninja,
            clickPower = 70,
            price = 90_000, // Было 30,000
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 9,
            name = "Ниндзя на перекуре",
            resourceName = Res.drawable.ninja_smoking,
            clickPower = 100,
            price = 150_000, // Было 50,000
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 10,
            name = "Надзиратель Игры",
            resourceName = Res.drawable.squid_game_guardian,
            clickPower = 150,
            price = 300_000, // Было 100,000
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 11,
            name = "Кроко-Бомбардир",
            resourceName = Res.drawable.bombardiro_crocadilo,
            clickPower = 220,
            price = 750_000, // Было 250,000
            isSelected = false,
            isUnlocked = false
        ),

        // --- УРОВЕНЬ 4: МУТАНТЫ ---
        GameCharacter(
            id = 12,
            name = "Зубастик",
            resourceName = Res.drawable.monster,
            clickPower = 350,
            price = 1_500_000, // Было 500,000
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 13,
            name = "Токсичный Геймер",
            resourceName = Res.drawable.atomic_waste,
            clickPower = 500,
            price = 3_000_000, // Было 1,000,000
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 14,
            name = "Аметистовый Бро",
            resourceName = Res.drawable.amethysto,
            clickPower = 750,
            price = 7_500_000, // Было 2,500,000
            isSelected = false,
            isUnlocked = false
        ),

        // --- УРОВЕНЬ 5: РОБЛОКС ЭЛИТА ---
        GameCharacter(
            id = 15,
            name = "Капитан Блокс",
            resourceName = Res.drawable.capitan_roblox_1,
            clickPower = 1_200,
            price = 15_000_000, // Было 5,000,000
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 16,
            name = "Космо-Сталкер",
            resourceName = Res.drawable.cosminus_5,
            clickPower = 2_000,
            price = 30_000_000, // Было 10,000,000
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 17,
            name = "Неоновый Гонщик",
            resourceName = Res.drawable.neon_overdrive_roblox_11,
            clickPower = 3_500,
            price = 75_000_000, // Было 25,000,000
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 18,
            name = "Тигр-Машина",
            resourceName = Res.drawable.tigris_roblox_10,
            clickPower = 5_000,
            price = 150_000_000, // Было 50,000,000
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 19,
            name = "Супер-Пупер Мэн",
            resourceName = Res.drawable.super_roblox_6,
            clickPower = 8_000,
            price = 300_000_000, // Было 100,000,000
            isSelected = false,
            isUnlocked = false
        ),

        // --- УРОВЕНЬ 6: ЛЕГЕНДЫ ---
        GameCharacter(
            id = 20,
            name = "Темная Материя",
            resourceName = Res.drawable.darkmatter,
            clickPower = 15_000,
            price = 1_500_000_000, // Было 500,000,000
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 21,
            name = "Агент 007",
            resourceName = Res.drawable.secret_roblox_7,
            clickPower = 30_000,
            price = 3_000_000_000, // Было 1,000,000,000
            isSelected = false,
            isUnlocked = false
        ),
        GameCharacter(
            id = 22,
            name = "Мистер Флекс",
            resourceName = Res.drawable.flex,
            clickPower = 100_000,
            price = 15_000_000_000, // Было 5,000,000,000
            isSelected = false,
            isUnlocked = false
        )
    )

    val allBackgrounds = listOf(
        Background(
            id = 1,
            name = "Пиксельный лес",
            resourceName = Res.drawable.minecraft_forest_background,
            price = 0, // Бесплатно
            isSelected = true,
            isUnlocked = true
        ),
        Background(
            id = 2,
            name = "Порт",
            resourceName = Res.drawable.roblox_background,
            price = 5 // 5 Гемов
        ),
        Background(
            id = 3,
            name = "Двери",
            resourceName = Res.drawable.roblox_doors_background,
            price = 15
        ),
        Background(
            id = 4,
            name = "Замок",
            resourceName = Res.drawable.roblox_castle_background,
            price = 30
        ),
        Background(
            id = 5,
            name = "Ковбои",
            resourceName = Res.drawable.brawl_starts_background,
            price = 50
        ),
        Background(
            id = 6,
            name = "Комната раздумий",
            resourceName = Res.drawable.tualet_shreka_background,
            price = 100
        ),
        Background(
            id = 7,
            name = "Надзератели",
            resourceName = Res.drawable.back_9,
            price = 200
        ),
        Background(
            id = 8,
            name = "Лес",
            resourceName = Res.drawable.back_3,
            price = 500
        ),
    )
}