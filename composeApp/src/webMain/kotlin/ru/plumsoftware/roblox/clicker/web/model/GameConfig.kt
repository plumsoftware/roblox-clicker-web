package ru.plumsoftware.roblox.clicker.web.model

import roblox_clicker_web.composeapp.generated.resources.*

object GameConfig {
    val allCharacters = listOf(
        // --- УРОВЕНЬ 1: НАЧАЛО ---
        GameCharacter(
            id = 1,
            name = "Боевой нобук",
            resourceName = Res.drawable.nubic,
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
            name = "Душнила",
            resourceName = Res.drawable.dushnila,
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
            name = "Ниндзя на пенсии",
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

    val allBoosts = listOf(
        // --- TIER 1: Слабые (За монеты) ---
        // Начальный уровень: школьники, нубы и дерево
        GameBoost(1, "нубик-питомец", Res.drawable.boost_1, income = 1, priceCoins = 100),
        GameBoost(2, "деревянная кирка", Res.drawable.boost_2, income = 5, priceCoins = 500, priceGems = 1),
        GameBoost(3, "кусты из бравла", Res.drawable.boost_3, income = 15, priceCoins = 1_500, priceGems = 5),
        GameBoost(4, "Жора", Res.drawable.boost_4, income = 40, priceCoins = 5_000, priceGems = 7),
        GameBoost(5, "бесплатные робуксы", Res.drawable.boost_5_v2, income = 100, priceCoins = 15_000, priceGems = 10),

        // --- TIER 2: Средние (Дорогие монеты) ---
        // Уровень "Продвинутый": трейды, мобы и донат
        GameBoost(6, "пудж", Res.drawable.boost_6, income = 250, priceCoins = 50_000, priceGems = 10),
        GameBoost(7, "житель", Res.drawable.boost_7_v2, income = 600, priceCoins = 150_000, priceGems = 15),
        GameBoost(8, "эль примо с ультой", Res.drawable.boost_8, income = 1_500, priceCoins = 500_000, priceGems = 20),
        GameBoost(9, "спавнер мобов", Res.drawable.boost_9, income = 4_000, priceCoins = 1_500_000, priceGems = 25),
        GameBoost(10, "голем", Res.drawable.boost_10_v2, income = 10_000, priceCoins = 3_000_000, priceGems = 30),

        // --- TIER 3: Мощные (За Гемы) ---
        // Уровень "Читер": админки, легендарки и мифы
        GameBoost(11, "лемон", Res.drawable.boost_11_v2, income = 25_000, priceGems = 50),
        GameBoost(12, "командный блок", Res.drawable.boost_12_v2, income = 60_000, priceGems = 75),
        GameBoost(13, "кубок", Res.drawable.boost_13, income = 150_000, priceGems = 100),
        GameBoost(14, "ничего", Res.drawable.boost_14, income = 400_000, priceGems = 150),
        GameBoost(15, "херобрин", Res.drawable.boost_15_v2, income = 1_000_000, priceGems = 300),

        // --- TIER 4: Легендарные (Монеты + Гемы) ---
        // Уровень "Бог": создатели игр и абсолютная власть
        GameBoost(16, "чиловая белка", Res.drawable.boost_16, income = 3_000_000, priceCoins = 100_000_000, priceGems = 500),
        GameBoost(17, "спайк", Res.drawable.boost_17, income = 8_000_000, priceCoins = 500_000_000, priceGems = 1000),
        GameBoost(18, "легендарный алмаз", Res.drawable.boost_18_v2, income = 20_000_000, priceCoins = 1_000_000_000, priceGems = 2000),
        GameBoost(19, "рубин", Res.drawable.boost_19_v2, income = 50_000_000, priceCoins = 10_000_000_000, priceGems = 5000),
        GameBoost(20, "???", Res.drawable.boost_20, income = 150_000_000, priceCoins = 100_000_000_000, priceGems = 10000)
    )

    fun getDailyQuests(): List<Quest> = listOf(
        // Легкие (Монеты)
        Quest(1, "100 кликов", QuestType.CLICKS, target = 100, rewardCoins = 100),
        Quest(2, "1К монет", QuestType.EARN_COINS, target = 1000, rewardCoins = 500),
        Quest(3, "Купи 1 улучшение", QuestType.BUY_ITEM, target = 1, rewardGems = 1),

        // Средние
        Quest(4, "1К кликов", QuestType.CLICKS, target = 1000, rewardCoins = 1500),
        Quest(5, "50К монет", QuestType.EARN_COINS, target = 50000, rewardCoins = 2500),
        Quest(6, "1 гем", QuestType.COLLECT_GEMS, target = 1, rewardCoins = 150),
        Quest(7, "Купи 3 улучшения", QuestType.BUY_ITEM, target = 3, rewardCoins = 5000),

        // Сложные (За Гемы)
        Quest(8, "50К кликов", QuestType.CLICKS, target = 5000, rewardGems = 1),
        Quest(9, "10М монет", QuestType.EARN_COINS, target = 1_000_000, rewardGems = 2),
        Quest(10, "10 гемов", QuestType.COLLECT_GEMS, target = 10, rewardGems = 3)
    )
}