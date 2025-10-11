package it.unibo.elementsduo.model.enemies.impl;

import it.unibo.elementsduo.model.enemies.api.EnemiesType;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.resources.Position;

public class ShooterEnemyImpl implements Enemy{

    private boolean alive = true;
    private double x;
    private double y;
    private int direction=1;
    private double speed=0.1;

    public ShooterEnemyImpl(char c, Position pos) {
        this.x= pos.x();
        this.y= pos.y();
        this.alive = true;

    }
    @Override
    public void move() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }

    @Override
    public void attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setDirection() {
        this.direction *= -1;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getDirection() {
        return this.direction;
    }

    @Override
    public EnemiesType getType() {
        return EnemiesType.S; 
        
    }
    
}
