package ru.arena;

public abstract class Hero implements Castable, Restable {
    public static final int MIN_STAT_VALUE = 1;

    private final String name;
    private final int maxHealth;
    private final int baseAttack;
    private int health;

    public Hero(String name, int maxHealth, int baseAttack) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "Безымянный Герой";
        } else {
            this.name = name.trim();
        }
        this.maxHealth = Math.max(MIN_STAT_VALUE, maxHealth);
        this.baseAttack = Math.max(MIN_STAT_VALUE, baseAttack);
        this.health = this.maxHealth;
    }

    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getHealth() {
        return health;
    }

    public abstract void attack(Hero target);

    public abstract ActionType makeTurn(Hero target);

    public void takeDamage(int damage) {
        if (damage <= 0 || !isAlive()) {
            return;
        }
        health = Math.max(0, health - damage);
        if (health == 0) {
            System.out.println(name + " пал в бою!");
        }
    }

    public void heal(int amount) {
        if (amount <= 0 || !isAlive()) {
            return;
        }
        health = Math.min(maxHealth, health + amount);
    }

    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public String toString() {
        return name + " [HP: " + health + "/" + maxHealth + ", Атака: " + baseAttack + "]";
    }
}