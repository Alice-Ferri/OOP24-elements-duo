package it.unibo.elementsduo.model.enemies.api;


public interface Enemy {
    void move();

    void attack();
    
    boolean isAlive();

    void setDirection();

    void update();

    double getX();

    double getY();

    double getDirection();

    EnemiesType getType();

}

