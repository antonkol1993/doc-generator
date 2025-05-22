package com.antonio.core.util;

public class AnsiColors {
    public static final String RESET = "\u001B[0m";

    public static final String RED = "\u001B[31m";       // ❌ ошибки
    public static final String GREEN = "\u001B[32m";     // ✅ успех
    public static final String YELLOW = "\u001B[33m";    // ⚠️ предупреждения
    public static final String ORANGE = "\u001B[38;5;208m"; // ‼️ явное предупреждение (оранжевый)
    public static final String BLUE = "\u001B[34m";      // ℹ️ информация
    public static final String CYAN = "\u001B[36m";      // 🛠 отладка
    public static final String MAGENTA = "\u001B[35m";   // 🧪 тесты, особые состояния

    public static String color(String message, String ansiColor) {
        return ansiColor + message + RESET;
    }
}
