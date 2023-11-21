package src.entity;

import src.ui.GameWindow;
import src.ui.GameMap;

public abstract class ATower implements ITower {

    /* -------------------------------- Attribute ------------------------------- */
    protected int damage;
    protected int towerLevel;
    protected int range;
    protected int targetnumber;
    protected AEnemy enemy;
    protected int cost;
    protected int ID;
    protected static int IDcount;
    protected int x, y;
    protected GameMap money;
    private int cooldown = 1000;
    private long lastAttackTime = 0;

    /* ------------------------------- Constructor ------------------------------ */

    public ATower(int x, int y, int damage, int towerLevel, int range, int targetnumber, int cost, int cooldown) {
        IDcount++;
        this.ID = IDcount;
        // ADD 16 TO CENTER THE CIRCLE
        this.x = x * 64 + 16;
        this.y = y * 64 + 16;
        this.damage = damage;
        this.towerLevel = towerLevel;
        this.range = range * 64; // RANGE IN BLOCK LENGTH
        this.targetnumber = targetnumber;
        this.cost = cost;
        this.cooldown = cooldown * 1000; // PUT TO MILLISECONDS
    }

    /* --------------------------------- Methods -------------------------------- */

    public void dealDamage(AEnemy enemy) {
        enemy.receiveDamage(damage);
    }

    public void upgradeTower() {
        if (towerLevel == 1 && money.getMoney() == 10) {
            upgradeDamage();
            upgradeRange();
            towerLevel++;
            // change de sprite
            // this.sprite = sprite level 2
        } else if (towerLevel == 2 && money.getMoney() == 20) {
            upgradeDamage();
            upgradeRange();
            towerLevel++;
            // this.sprite = sprite level 3
        } else {
            System.out.println("Level Max");
        }
    }

    /* -------------------------------- Cooldown -------------------------------- */

    public boolean isReadyToAttack() {
        long currentTime = System.currentTimeMillis();
        return currentTime - lastAttackTime >= cooldown;
    }

    public void resetCooldown() {
        lastAttackTime = System.currentTimeMillis();
    }

    /* ---------------------------------- Maths --------------------------------- */

    public double calculateDistance(int targetX, int targetY) {
        return Math.sqrt(Math.pow(targetX - x, 2) + Math.pow(targetY - y, 2));
    }

    public boolean isInRange(AEnemy e) {
        double distance = calculateDistance((int) e.getX(), (int) e.getY());
        return distance <= range;
    }

    /* --------------------------------- Getters -------------------------------- */

    public void upgradeDamage() {
        this.damage += 5;
    }

    public void upgradeRange() {
        this.range += 1;
    }

    public int getLevel() {
        return this.towerLevel;
    }

    public int Targetnumber() {
        return this.targetnumber;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getRange() {
        return this.range;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getCost() {
        return cost;
    }
}
