package it.unibo.elementsduo.model.enemies.impl;


import it.unibo.elementsduo.model.enemies.api.EnemiesType;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.resources.Position;

/* Standard enemies that move around the level and inflict damage when the player touches them. */
public class ClassicEnemiesImpl implements Enemy {
    private boolean alive = true;
    private double x;
    private double y;
    private int direction=1;
    private double speed=0.05;

    public ClassicEnemiesImpl(final char c, final Position pos) {
        this.x= pos.x();
        this.y= pos.y();
        this.alive = true;
    }

    @Override
    public void move() {
        if (alive) {
            x += direction * speed;
        }
        //need to implements collision with walls 
    }

    @Override
    public void attack() {

        // Classic enemy does not attack, so this method does nothing
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

     public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public double getDirection() {
        return direction;
    }

    @Override
    public void setDirection() {
        this.direction*=-1;
    }

    @Override
    public EnemiesType getType() {
        return EnemiesType.C;
    }
}
