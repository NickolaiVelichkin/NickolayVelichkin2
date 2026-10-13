package ru.arena;

public class Warrior extends Hero implements Castable, Restable {
    private final int armor;

    public Warrior(String name, int maxHealth, int baseAttack, int armor) {
        super(name, maxHealth, baseAttack);
        this.armor = Math.max(MIN_STAT_VALUE, armor);
    }

    public int getArmor() {
        return armor;
    }

    @Override
    public void takeDamage(int damage) {
        if (damage <= 0 || !isAlive()) {
            return;
        }
        int finalDamage = Math.max(1, damage - armor);
        System.out.println("Броня воина " + getName() + " поглотила часть урона. Получено чистого урона: " + finalDamage);
        super.takeDamage(finalDamage);
    }

    @Override
    public void attack(Hero target) {
        if (target == null) {
            return;
        }
        System.out.println("Воин " + getName() + " наносит рубящий удар мечом по " + target.getName()
                + " на " + getBaseAttack() + " ед. урона.");
        target.takeDamage(getBaseAttack());
    }

    @Override
    public ActionType makeTurn(Hero target) {
        if (target == null) {
            return ActionType.REST;
        }
        if (canCast()) {
            castSpecialSkill(target);
            return ActionType.SPECIAL_SKILL;
        }
        if (needsRest()) {
            rest();
            return ActionType.REST;
        }
        attack(target);
        return ActionType.BASE_ATTACK;
    }

    @Override
    public boolean canCast() {
        return getHealth() < getMaxHealth() * 0.5;
    }

    @Override
    public void castSpecialSkill(Hero target) {
        if (target == null) {
            return;
        }
        if (!canCast()) {
            System.out.println("Воин попытался ударить щитом, но потерял равновесие!");
            return;
        }
        int specialDamage = getBaseAttack() + (armor * 2);
        System.out.println("Воин " + getName() + " применяет умение «Удар щитом» по " + target.getName()
                + " и наносит " + specialDamage + " ед. урона!");
        target.takeDamage(specialDamage);
    }

    @Override
    public boolean needsRest() {
        return getHealth() < getMaxHealth() * 0.15;
    }

    @Override
    public void rest() {
        int healAmount = armor * 2;
        heal(healAmount);
        System.out.println("Воин " + getName() + " использует «Второе дыхание» и восстанавливает "
                + healAmount + " ед. здоровья (Текущее HP: " + getHealth() + "/" + getMaxHealth() + ").");
    }

    @Override
    public String toString() {
        return "Воин " + getName() + " [HP: " + getHealth() + "/" + getMaxHealth()
                + ", Атака: " + getBaseAttack() + ", Броня: " + armor + "]";
    }
}