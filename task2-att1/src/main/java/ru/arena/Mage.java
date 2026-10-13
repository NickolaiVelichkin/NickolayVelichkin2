package ru.arena;

public class Mage extends Hero implements Castable, Restable {
    private final int maxMana;
    private int mana;

    public Mage(String name, int maxHealth, int baseAttack, int maxMana) {
        super(name, maxHealth, baseAttack);
        this.maxMana = Math.max(MIN_STAT_VALUE, maxMana);
        this.mana = this.maxMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getMana() {
        return mana;
    }

    @Override
    public void attack(Hero target) {
        if (target == null) {
            return;
        }
        if (mana >= 10) {
            int spellDamage = getBaseAttack() * 2;
            mana -= 10;
            System.out.println("Маг " + getName() + " выпускает «Магический выстрел» в " + target.getName()
                    + " на " + spellDamage + " ед. урона (Потрачено 10 маны, остаток: " + mana + ").");
            target.takeDamage(spellDamage);
        } else {
            int staffDamage = getBaseAttack() / 2;
            mana = Math.min(maxMana, mana + 5);
            System.out.println("Маг " + getName() + " совершает «Удар посохом» по " + target.getName()
                    + " на " + staffDamage + " ед. урона и восстанавливает ману (Текущая мана: " + mana + "/" + maxMana + ").");
            target.takeDamage(staffDamage);
        }
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
        return mana >= 25;
    }

    @Override
    public void castSpecialSkill(Hero target) {
        if (target == null) {
            return;
        }
        if (!canCast()) {
            System.out.println("Маг " + getName() + " попытался сотворить «Огненную глыбу», но не хватило маны! Заклинание сорвалось.");
            return;
        }
        int fireballDamage = getBaseAttack() * 3;
        mana -= 25;
        System.out.println("Маг " + getName() + " обрушивает «Огненную глыбу» на " + target.getName()
                + ", нанося " + fireballDamage + " ед. урона! (Потрачено 25 маны, остаток: " + mana + ").");
        target.takeDamage(fireballDamage);
    }

    @Override
    public boolean needsRest() {
        return mana == 0 || getHealth() < getMaxHealth() * 0.3;
    }

    @Override
    public void rest() {
        mana = maxMana;
        heal(getBaseAttack());
        System.out.println("Маг " + getName() + " проводит «Исцеляющую медитацию»: мана полностью восстановлена ("
                + mana + "/" + maxMana + "), здоровье восстановлено на " + getBaseAttack()
                + " ед. (Текущее HP: " + getHealth() + "/" + getMaxHealth() + ").");
    }

    @Override
    public String toString() {
        return "Маг " + getName() + " [HP: " + getHealth() + "/" + getMaxHealth()
                + ", Атака: " + getBaseAttack() + ", Мана: " + mana + "/" + maxMana + "]";
    }
}