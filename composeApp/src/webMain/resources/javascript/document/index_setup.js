// Блокировка контекстного меню (ПКМ)
document.addEventListener('contextmenu', function(event) {
    event.preventDefault();
});

// Блокировка горячих клавиш
document.onkeydown = function(e) {
    // F12
    if (e.keyCode == 123) {
        return false;
    }
    // Ctrl+Shift+I / J (Консоль)
    if (e.ctrlKey && e.shiftKey && (e.keyCode == 73 || e.keyCode == 74)) {
        return false;
    }
    // Ctrl+U (Исходный код)
    if (e.ctrlKey && e.keyCode == 85) {
        return false;
    }
}