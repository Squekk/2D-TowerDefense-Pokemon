package src.entity;

public abstract class ACastle implements ICastle {
    /* -------------------------------- Attribute ------------------------------- */

    protected final int MAXLIFE;
    protected int life;
    protected AEnemy enemy;

    /* ------------------------------- Constructor ------------------------------ */

    public ACastle(int life) {
        this.life = life;
        this.MAXLIFE = life;
    }

    /* --------------------------------- Methods -------------------------------- */

    public void looselife(int damage) {
        this.life -= damage;
    }

    /* --------------------------------- Getters -------------------------------- */

    public void setLife(int life) {
        this.life = life;
    }

    public int getLife() {
        return this.life;
    }

    public int getMAXLIFE() {
        return MAXLIFE;
    }

}
