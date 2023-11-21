package src.entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import src.ui.GameWindow;
import src.util.Movement;

public abstract class AEnemy implements IEnemy {

    /* ------------------------------- Attributes ------------------------------- */
    
    protected int damage;
    protected int health;
    protected int gold;
    protected float speed;
    protected int ID;
    private ArrayList<AEnemy> enemies = new ArrayList<>();
    protected Rectangle hitbox;
    protected int upgrade = 0;
    protected static int IDcount;
    protected float x;
    protected float y;
    protected final int MAX_HEALTH;
    protected DIR direction;

    public enum DIR {
        RIGHT,
        LEFT,
        UP,
        DOWN
    }

    /* ------------------------------- Constructor ------------------------------ */

    public AEnemy(int health, float x, float y, float speed, int gold, int damage) {
        IDcount++;
        this.MAX_HEALTH = health;
        this.speed = speed;
        this.ID = IDcount;
        this.health = health;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.gold = gold;
        this.damage = damage;
    }

    /* --------------------------------- Methods -------------------------------- */

    public void move(DIR direction) {
        switch (direction) {
            case LEFT:
                this.x -= speed;
                break;
            case UP:
                this.y -= speed;
                break;
            case RIGHT:
                this.x += speed;
                break;
            case DOWN:
                this.y += speed;
                break;
        }
    }

    public void receiveDamage(int damage) {
        this.health -= damage;
        System.out.println("enemy h:" + this.health);
    }

    public void updateGold(int gold) {
        this.gold = gold;
    }

    /* --------------------------------- Getters -------------------------------- */
    public int getDamage() {
        return this.damage;
    }

    public int getHealth() {
        return this.health;
    }

    public float getSpeed() {
        return this.speed;
    }

    public int getGold() {
        return this.gold;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    public ArrayList<AEnemy> getEnemies() {
        return enemies;
    }

}
