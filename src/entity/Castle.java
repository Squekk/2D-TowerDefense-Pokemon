package src.entity;

public class Castle extends ACastle {

    public Castle(int life) {
        super(life);
    }

    public void looselife(int damage){
        this.life -= damage;
    } 
}