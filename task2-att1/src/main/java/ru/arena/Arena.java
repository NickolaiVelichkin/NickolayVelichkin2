package ru.arena;

public class Arena {
    private final Hero fighter1;
    private final Hero fighter2;
    private int roundCounter;

    public Arena(Hero fighter1, Hero fighter2) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
        this.roundCounter = 0;
    }

    public void startTournament() {
        if (fighter1 == null || fighter2 == null) {
            System.out.println("Ошибка: для начала турнира необходимы два бойца!");
            return;
        }

        System.out.println("ТУРНИР НА АРЕНЕ НАЧИНАЕТСЯ!");
        System.out.println("Боец 1: " + fighter1);
        System.out.println("Боец 2: " + fighter2);

        while (fighter1.isAlive() && fighter2.isAlive()) {
            roundCounter++;
            System.out.println("\nРАУНД " + roundCounter);

            Hero attacker;
            Hero defender;
            if (Math.random() < 0.5) {
                attacker = fighter1;
                defender = fighter2;
            } else {
                attacker = fighter2;
                defender = fighter1;
            }

            System.out.println("Первым в этом раунде ходит: " + attacker.getName());

            ActionType firstAction = attacker.makeTurn(defender);
            System.out.println("Игрок " + attacker.getName() + " совершил действие: " + firstAction.getDescription());

            if (!defender.isAlive()) {
                break;
            }

            System.out.println("Ответный ход");
            ActionType secondAction = defender.makeTurn(attacker);
            System.out.println("Игрок " + defender.getName() + " совершил действие: " + secondAction.getDescription());

            if (!attacker.isAlive()) {
                break;
            }

            System.out.println("\n[Статус в конце раунда " + roundCounter + "]");
            System.out.println("  • " + fighter1);
            System.out.println("  • " + fighter2);
        }

        System.out.println("\n");
        Hero winner = fighter1.isAlive() ? fighter1 : fighter2;
        System.out.println("ПОБЕДИТЕЛЬ ТУРНИРА: " + winner.getName() + "!");
        System.out.println("Итоговое состояние победителя: " + winner);
        System.out.println("Бой завершился на раунде: " + roundCounter);
    }
}