package ru.plumsoftware.roblox.clicker.web.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.plumsoftware.roblox.clicker.web.model.GameCharacter
import ru.plumsoftware.roblox.clicker.web.model.GameConfig
import ru.plumsoftware.roblox.clicker.web.model.GamerData
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.screens_dialogs.MainScreenDialog
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.screens_dialogs.MainScreenScreens
import ru.plumsoftware.roblox.clicker.web.utils.AudioManager
import ru.plumsoftware.roblox.clicker.web.ya.YandexGamesManager

class MainScreenViewModel : ViewModel() {

    val effects = MutableSharedFlow<MainScreenPack.Effect>()

    // State
    val state = MutableStateFlow(MainScreenPack.MainScreenState.default())

    init {
        loadProgress()
        startAutoSave()

        // При старте считаем, какая должна быть цель
        recalculateGemTarget(state.value.gamerData)
        updateCharactersList(state.value.gamerData)
    }

    // --- ЛОГИКА ДАННЫХ (ЭКОНОМИКА) ---
    private fun loadProgress() {
        viewModelScope.launch {
            // Ждем инициализации
            while (!YandexGamesManager.isInitialized) {
                delay(100)
            }

            println("MainVM: Loading player data...")
            var loadedData = YandexGamesManager.loadGame()

            // Если сохранений нет, берем дефолтный GamerData (где уже прописан ID 1)
            if (loadedData == null) {
                loadedData = GamerData()
            } else {
                // ЗАЩИТА ОТ СТАРЫХ СОХРАНЕНИЙ:
                // Если вдруг пришел id=0 (старый баг), принудительно ставим 1
                if (loadedData.selectedSkinId == 0) {
                    loadedData = loadedData.copy(selectedSkinId = 1)
                }
                // Если список купленных пуст (старый баг), добавляем туда 1
                if (loadedData.unlockedCharacterIds.isEmpty()) {
                    loadedData = loadedData.copy(unlockedCharacterIds = listOf(1))
                }
            }

            // Обновляем состояние
            state.update {
                it.copy(
                    gamerData = loadedData,
                    // Сразу обновляем визуальный список
                    charactersList = mapDataToCharacters(loadedData)
                )
            }
            println("MainVM: Data loaded! Coins: ${loadedData.coins}")
        }.invokeOnCompletion {
            setupClickPowerForGems()
        }
    }

    private fun startAutoSave() {
        viewModelScope.launch {
            while (true) {
                delay(30_000)
                if (YandexGamesManager.isInitialized) {
                    YandexGamesManager.saveGame(state.value.gamerData)
                    println("MainVM: Auto-saved!")
                }
            }
        }
    }

    fun changeIsSoundOn() {
        state.update {
            it.copy(isSoundOn = !state.value.isSoundOn)
        }
        if (state.value.isSoundOn) {
            AudioManager.playSound(
                fileName = "Sakura-Girl-Cat-Walk-chosic.com_.mp3",
                volume = 0.05
            )
        } else {
            AudioManager.stopMusic()
        }
    }

    fun onMainCharacterClick() {

//        if (!state.value.isStartedBgMusic) {
//            AudioManager.playSound(
//                fileName = "Sakura-Girl-Cat-Walk-chosic.com_.mp3",
//                volume = 0.05
//            )
//            state.update { it.copy(isStartedBgMusic = true) }
//        }

        state.update { oldState ->
            val oldData = oldState.gamerData
            val currentCharacter = GameConfig.allCharacters.find { it.id == oldData.selectedSkinId }
            val power = currentCharacter?.clickPower ?: 1

            val newCoins = oldData.coins + power

            // Логика Гемификации
            var newProgress = oldData.clickProgressForGems + power
            var newUnclaimedGems = oldData.unclaimedGems // Работаем с unclaimed

            val target =
                if (oldState.maxClickProgressForGems > 0) oldState.maxClickProgressForGems else 100.0

            if (newProgress >= target) {
                // УРА! Гем падает в "копилку" (unclaimed), а не сразу в баланс
                newUnclaimedGems++
                newProgress = 0.0 // Сброс
            }

            val newData = oldData.copy(
                coins = newCoins,
                unclaimedGems = newUnclaimedGems, // Обновляем копилку
                clickProgressForGems = newProgress
            )

            // Сложность пересчитываем от СУММЫ (имеющиеся + в копилке), чтобы не абузили
            val totalGems = oldData.gems + newUnclaimedGems

            val newMaxProgress = if (newUnclaimedGems > oldData.unclaimedGems) {
                calculateTarget(power, totalGems)
            } else {
                oldState.maxClickProgressForGems
            }

            oldState.copy(
                gamerData = newData,
                maxClickProgressForGems = newMaxProgress
            )
        }
        if (state.value.isSoundOn) {
            AudioManager.playSound("minecraft-click.mp3")
        }
    }

    // --- ЛОГИКА СБОРА ГЕМОВ ---
    private fun claimGems() {
        val currentUnclaimed = state.value.gamerData.unclaimedGems
        if (currentUnclaimed <= 0) return

        state.update { oldState ->
            val oldData = oldState.gamerData

            // 1. Переносим из копилки в основной баланс
            val newData = oldData.copy(
                gems = oldData.gems + currentUnclaimed,
                unclaimedGems = 0
            )

            // 2. Показываем диалог
            oldState.copy(
                gamerData = newData,
                currentMainScreenDialog = MainScreenDialog.MainDialog.ClaimGemsDialog(
                    currentUnclaimed
                )
            )
        }
        // Сохраняем сразу, так как изменилась важная валюта
        saveGameImmediately()
    }

    private fun closeDialog() {
        state.update { it.copy(currentMainScreenDialog = MainScreenDialog.Empty) }
    }

    fun onShopItemClick(character: GameCharacter) {
        val currentData = state.value.gamerData

        // Сценарий 1: Персонаж уже куплен -> Просто выбираем
        if (currentData.unlockedCharacterIds.contains(character.id)) {
            selectCharacter(character.id)
            return
        }

        // Сценарий 2: Персонаж не куплен -> Пробуем купить
        if (currentData.coins >= character.price) {
            buyAndSelectCharacter(character)
        } else {
            // Эффект "Недостаточно денег" (можно добавить вибрацию или тост)
            println("Not enough money!")
        }
    }

    private fun buyAndSelectCharacter(character: GameCharacter) {
        state.update { oldState ->
            val oldData = oldState.gamerData

            // 1. Списываем деньги
            // 2. Добавляем ID в список купленных
            // 3. Ставим ID как выбранный
            val newData = oldData.copy(
                coins = oldData.coins - character.price,
                unlockedCharacterIds = oldData.unlockedCharacterIds + character.id,
                selectedSkinId = character.id
            )

            oldState.copy(
                gamerData = newData,
                charactersList = mapDataToCharacters(newData)
            )
        }
        // Сразу сохраняем при важной покупке
        viewModelScope.launch {
            if (YandexGamesManager.isInitialized) {
                YandexGamesManager.saveGame(state.value.gamerData)
            }
        }
        AudioManager.playSound("buy-1.mp3")
    }

    private fun setupClickPowerForGems() {
        val currentCLickPower =
            GameConfig.allCharacters.first { it.id == state.value.gamerData.selectedSkinId }.clickPower
        val gemMultiplier = if (state.value.gamerData.gems == 0L) 1 else state.value.gamerData.gems
        val clickPowerForGems = currentCLickPower / (10.0 * gemMultiplier.toDouble())

        state.update {
            it.copy(
                clickPowerForGems = clickPowerForGems,
                maxClickProgressForGems = currentCLickPower.toDouble()
            )
        }

        recalculateGemTarget(state.value.gamerData)
    }

    // --- ЛОГИКА ПЕРЕСЧЕТА ЦЕЛИ ---

    // Вызывай это при смене персонажа и при загрузке
    private fun recalculateGemTarget(data: GamerData) {
        val currentCharacter = GameConfig.allCharacters.find { it.id == data.selectedSkinId }
        val power = currentCharacter?.clickPower ?: 1
        val totalGems = data.gems + data.unclaimedGems
        val newTarget = calculateTarget(power, totalGems)
        state.update { it.copy(maxClickProgressForGems = newTarget) }
    }

    // Формула сложности
    private fun calculateTarget(heroPower: Long, currentGems: Long): Double {
        // База: нужно сделать 100 кликов текущим героем, чтобы получить гем.
        // Усложнение: Каждый гем увеличивает требование на 5% (коэффициент 0.05)
        // Пример: 0 гемов -> 100 кликов. 10 гемов -> 150 кликов.
        val baseClicksNeeded = 50 // Сколько кликов нужно для 1-го гема
        val difficultyMultiplier = 1.0 + (currentGems * 0.05)

        return (heroPower * baseClicksNeeded * difficultyMultiplier)
    }

    // --- ОБНОВЛЕНИЕ ПРИ СМЕНЕ ГЕРОЯ ---

    private fun selectCharacter(id: Int) {
        state.update { oldState ->
            val newData = oldState.gamerData.copy(selectedSkinId = id)
            oldState.copy(
                gamerData = newData,
                charactersList = mapDataToCharacters(newData)
            )
        }
        // ВАЖНО: При смене героя пересчитываем цель,
        // иначе сильным героем набьешь гемы за секунду по старой цели
        recalculateGemTarget(state.value.gamerData)
    }

    // --- ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ---
    private fun updateCharactersList(data: GamerData) {
        state.update { it.copy(charactersList = mapDataToCharacters(data)) }
    }

    // Маппер: GameConfig + GamerData -> List<GameCharacter>
    private fun mapDataToCharacters(data: GamerData): List<GameCharacter> {
        return GameConfig.allCharacters.map { staticChar ->
            staticChar.copy(
                // Персонаж разблокирован, если его ID есть в списке unlockedCharacterIds
                isUnlocked = data.unlockedCharacterIds.contains(staticChar.id),

                // Персонаж выбран, если его ID совпадает с selectedSkinId
                isSelected = data.selectedSkinId == staticChar.id
            )
        }
    }

    // --- ЛОГИКА UI (НАВИГАЦИЯ) ---
    fun onEvent(event: MainScreenPack.Event) {
        when (event) {
            is MainScreenPack.Event.onSettingsClick -> {
                updateScreenState(
                    dialog = MainScreenDialog.MainDialog.SettingsDialog(),
                    effect = MainScreenPack.Effect.onSettingsClicked
                )
            }

            is MainScreenPack.Event.onBackClick -> {
                updateScreenState(
                    screen = MainScreenScreens.Shop.BackShop(),
                    effect = MainScreenPack.Effect.onBackClicked
                )
            }

            is MainScreenPack.Event.onBoostClick -> {
                updateScreenState(
                    screen = MainScreenScreens.Shop.BoostShop(),
                    effect = MainScreenPack.Effect.onBoostClicked
                )
            }

            is MainScreenPack.Event.onHeroClick -> {
                updateScreenState(
                    screen = MainScreenScreens.Shop.HeroShop(),
                    effect = MainScreenPack.Effect.onHeroClicked
                )
            }

            is MainScreenPack.Event.onSoundsClick -> {
                updateScreenState(
                    screen = MainScreenScreens.Shop.SoundShop(),
                    effect = MainScreenPack.Effect.onSoundsClicked
                )
            }

            is MainScreenPack.Event.onClaimGemsClick -> claimGems() // Нажали на карточку

            is MainScreenPack.Event.onCloseDialog -> closeDialog()  // Нажали "Забрать" в диалоге
        }
    }

    // Вспомогательная для сохранения
    private fun saveGameImmediately() {
        viewModelScope.launch {
            if (YandexGamesManager.isInitialized) {
                YandexGamesManager.saveGame(state.value.gamerData)
            }
        }
    }

    private fun updateScreenState(
        screen: MainScreenScreens.Shop? = null,
        dialog: MainScreenDialog.MainDialog? = null,
        effect: MainScreenPack.Effect
    ) {
        viewModelScope.launch {
            state.update { currentState ->
                var newState = currentState

                if (screen != null) {
                    newState = newState.copy(currentScreen = screen)
                }
                if (dialog != null) {
                    newState = newState.copy(currentMainScreenDialog = dialog)
                }

                // Теперь здесь ошибки не будет
                newState.copy(shopScreenName = getShopName(newState.currentScreen))
            }
            effects.emit(effect)
        }
    }

    private fun getShopName(currentScreen: MainScreenScreens): String {
        return when (currentScreen) {
            is MainScreenScreens.Shop.BackShop -> "задний фон"
            is MainScreenScreens.Shop.BoostShop -> "бусты и улучшения"
            is MainScreenScreens.Shop.HeroShop -> "персонажи"
            is MainScreenScreens.Shop.SoundShop -> "звуки"
        }
    }

    companion object {
        val koinVM = module {
            viewModelOf(::MainScreenViewModel)
        }
    }
}