package ru.arena;

public enum ActionType {
    BASE_ATTACK("Базовая атака"),
    SPECIAL_SKILL("Применение особого умения или магии"),
    REST("Пропуск хода ради восстановления сил");

    private final String description;

    ActionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}