// Объявляем глобальные переменные, чтобы они были видны везде
window.ysdk = null;
window.yplayer = null;

// Эта функция вызывается из Kotlin
function initYandexSdk() {
    console.log("[JS Bridge] Начинаем инициализацию Yandex SDK...");

    // 1. Проверяем, загрузился ли вообще скрипт Яндекса (sdk.js)
    if (typeof YaGames === 'undefined') {
        console.warn("[JS Bridge] YaGames не найден. Скорее всего, вы запустили игру локально или скрипт не загрузился.");
        // Возвращаем Promise, который разрешается в false (ошибка инициализации)
        // Но не reject, чтобы игра не упала, а просто поняла, что SDK нет.
        return Promise.resolve(false);
    }

    // 2. Инициализируем SDK
    return YaGames.init()
        .then(sdk => {
            console.log("[JS Bridge] Основной SDK инициализирован!");
            window.ysdk = sdk;

            // 3. Сразу инициализируем Игрока (нужен для сохранений и покупок)
            // { scopes: false } - Важно! Не запрашиваем имя и фото сразу, чтобы не пугать юзера окном авторизации.
            return sdk.getPlayer({ scopes: false });
        })
        .then(player => {
            console.log("[JS Bridge] Объект Игрока получен!");
            window.yplayer = player;

            // Всё прошло успешно, возвращаем true в Kotlin
            return true;
        })
        .catch(err => {
            console.error("[JS Bridge] Ошибка инициализации:", err);

            // Если SDK загрузился, но игрока не получили (например, проблемы с сетью или гость)
            // Мы всё равно считаем, что SDK работает (рекламу показывать можно)
            if (window.ysdk) {
                console.log("[JS Bridge] SDK работает, но без Игрока (реклама будет работать).");
                return true;
            }

            // Если всё совсем плохо — возвращаем false
            return false;
        });
}