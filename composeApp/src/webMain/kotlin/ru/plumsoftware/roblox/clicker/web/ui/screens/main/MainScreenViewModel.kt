package ru.plumsoftware.roblox.clicker.web.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.plumsoftware.roblox.clicker.web.model.GameCharacter
import ru.plumsoftware.roblox.clicker.web.model.GameConfig
import ru.plumsoftware.roblox.clicker.web.model.GamerData
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.screens_dialogs.MainScreenDialog
import ru.plumsoftware.roblox.clicker.web.ui.screens.main.screens_dialogs.MainScreenScreens
import ru.plumsoftware.roblox.clicker.web.ya.YandexGamesManager

class MainScreenViewModel : ViewModel() {

    val effects = MutableSharedFlow<MainScreenPack.Effect>()

    // State
    val state = MutableStateFlow(MainScreenPack.MainScreenState.default())

    init {
        loadProgress()
        startAutoSave()

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

    fun onMainCharacterClick() {
        state.update { oldState ->
            val oldData = oldState.gamerData

            // Находим силу клика текущего выбранного персонажа
            val currentCharacter = GameConfig.allCharacters.find { it.id == oldData.selectedSkinId }
            val power = currentCharacter?.clickPower ?: 1

            val newData = oldData.copy(coins = oldData.coins + power)

            oldState.copy(gamerData = newData)
        }
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

    private fun selectCharacter(id: Int) {
        state.update { oldState ->
            val newData = oldState.gamerData.copy(selectedSkinId = id)
            oldState.copy(
                gamerData = newData,
                charactersList = mapDataToCharacters(newData)
            )
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

    // ИСПРАВЛЕНО ЗДЕСЬ:
    // 1. Принимаем общий тип MainScreenScreens (а не Shop)
    // 2. Добавили ветку else для экранов, которые не являются магазином
    private fun getShopName(currentScreen: MainScreenScreens) : String {
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