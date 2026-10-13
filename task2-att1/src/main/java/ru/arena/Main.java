package ru.arena;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в симулятор «Арена Героев»!");
        Hero fighter1 = createHero(scanner, 1);
        Hero fighter2 = createHero(scanner, 2);

        Arena arena = new Arena(fighter1, fighter2);
        arena.startTournament();

        scanner.close();
    }

    private static Hero createHero(Scanner scanner, int heroNumber) {
        System.out.println("\nСоздание Бойца №" + heroNumber);

        int type = 0;
        while (type != 1 && type != 2) {
            type = readPositiveInt(scanner, "Выберите тип персонажа (1 — Воин, 2 — Маг): ");
            if (type != 1 && type != 2) {
                System.out.println("Пожалуйста, введите 1 или 2.");
            }
        }

        String name = readNonEmptyString(scanner, "Введите имя персонажа: ");
        int maxHealth = readPositiveInt(scanner, "Введите максимальное здоровье (HP >= 1): ");
        int baseAttack = readPositiveInt(scanner, "Введите базовую силу атаки (>= 1): ");

        if (type == 1) {
            int armor = readPositiveInt(scanner, "Введите показатель брони Воина (>= 1): ");
            return new Warrior(name, maxHealth, baseAttack, armor);
        } else {
            int maxMana = readPositiveInt(scanner, "Введите максимальный запас маны Мага (>= 1): ");
            return new Mage(name, maxHealth, baseAttack, maxMana);
        }
    }

    private static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Имя не может быть пустым. Попробуйте снова.");
        }
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= Hero.MIN_STAT_VALUE) {
                    return value;
                }
                System.out.println("Значение должно быть не меньше " + Hero.MIN_STAT_VALUE + ".");
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Пожалуйста, введите целое число.");
            }
        }
    }
}