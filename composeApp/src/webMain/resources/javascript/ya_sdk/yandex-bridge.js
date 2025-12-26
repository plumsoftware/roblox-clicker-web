// -------------------------------------------------------------
// Глобальные переменные для хранения состояния SDK
// -------------------------------------------------------------
window.ysdk = null;
window.yplayer = null;

// -------------------------------------------------------------
// 1. Инициализация SDK
// Вызывается из Kotlin: YandexManager.init()
// -------------------------------------------------------------
function initYandexSdk() {
    console.log("[JS Bridge] Начинаем инициализацию Yandex SDK...");

    // Проверка на локальный запуск (защита от ошибок на localhost)
    if (typeof YaGames === 'undefined') {
        console.warn("[JS Bridge] YaGames не найден. Вы на localhost или скрипт не загружен.");
        // Возвращаем false, но не ломаем приложение ошибкой
        return Promise.resolve(false);
    }

    // Инициализация
    return YaGames.init()
        .then(sdk => {
            console.log("[JS Bridge] SDK инициализирован!");
            window.ysdk = sdk;

            // Инициализация Игрока (нужна для сохранений)
            // scopes: false - не требуем авторизации сразу
            return sdk.getPlayer({ scopes: false });
        })
        .then(player => {
            console.log("[JS Bridge] Игрок получен!");
            window.yplayer = player;
            return true; // Успех -> Kotlin получает true
        })
        .catch(err => {
            console.error("[JS Bridge] Ошибка инициализации:", err);

            // Если SDK есть, но игрока нет (ошибка сети/гость) - возвращаем true,
            // так как рекламу показывать всё равно можно.
            if (window.ysdk) {
                return true;
            }
            return false;
        });
}

// -------------------------------------------------------------
// 2. Сохранение данных
// Вызывается из Kotlin: YandexManager.saveGame(GamerData)
// Принимает: JSON-строку
// -------------------------------------------------------------
function savePlayerData(jsonString) {
    if (!window.yplayer) {
        console.warn("[JS Bridge] Не могу сохранить: Игрок не инициализирован.");
        return Promise.resolve(false);
    }

    try {
        // Превращаем строку от Kotlin обратно в JS-объект
        const data = JSON.parse(jsonString);
        console.log("[JS Bridge] Отправка данных в облако:", data);

        // Отправляем в Яндекс
        return window.yplayer.setData(data, false)
            .then(() => {
                console.log("[JS Bridge] Данные успешно сохранены.");
                return true;
            })
            .catch(err => {
                console.error("[JS Bridge] Ошибка сохранения в Яндекс:", err);
                return false;
            });
    } catch (e) {
        console.error("[JS Bridge] Ошибка парсинга JSON перед сохранением:", e);
        return Promise.resolve(false);
    }
}

// -------------------------------------------------------------
// 3. Загрузка данных
// Вызывается из Kotlin: YandexManager.loadGame()
// Возвращает: JSON-строку
// -------------------------------------------------------------
function loadPlayerData() {
    if (!window.yplayer) {
        console.warn("[JS Bridge] Не могу загрузить: Игрок не инициализирован.");
        // Возвращаем пустую строку, Kotlin поймет это как "нет данных"
        return Promise.resolve("");
    }

    return window.yplayer.getData()
        .then(data => {
            console.log("[JS Bridge] Данные получены из облака:", data);

            // Превращаем JS-объект в строку, чтобы передать в Kotlin
            // Если данных нет, вернется "{}"
            return JSON.stringify(data);
        })
        .catch(err => {
            console.error("[JS Bridge] Ошибка загрузки из Яндекс:", err);
            return "";
        });
}

function gameReady() {
    if (window.ysdk && window.ysdk.features && window.ysdk.features.LoadingAPI) {
        console.log("[JS Bridge] Отправляем Game Ready...");
        window.ysdk.features.LoadingAPI.ready();
    } else {
        console.warn("[JS Bridge] Game Ready API недоступен (возможно, SDK не инициализирован).");
    }
}

// --- Исправление "Системного плеера" ---
if ('mediaSession' in navigator) {
    navigator.mediaSession.setActionHandler('play', function() {});
    navigator.mediaSession.setActionHandler('pause', function() {});
    navigator.mediaSession.setActionHandler('seekbackward', null);
    navigator.mediaSession.setActionHandler('seekforward', null);
    navigator.mediaSession.setActionHandler('previoustrack', null);
    navigator.mediaSession.setActionHandler('nexttrack', null);
}

function getLang() {
    if (window.ysdk && window.ysdk.environment && window.ysdk.environment.i18n) {
        return window.ysdk.environment.i18n.lang;
    }
    return "ru"; // Дефолт
}