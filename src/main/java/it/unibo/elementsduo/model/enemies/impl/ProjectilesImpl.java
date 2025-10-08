package it.unibo.elementsduo.model.enemies.impl;

import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.resources.Position;

public class ProjectilesImpl implements Projectiles {
    private double x, y, speed = 0.05;
    private int direction;
    private boolean alive = true;

    public ProjectilesImpl(Position pos, int direction) {
        this.x = pos.x();
        this.y = pos.y();
        this.direction = direction;
    }

    @Override
    public void update() {
        x += direction * speed;

        //Position tilePos = new Position((int)Math.floor(x), (int)Math.floor(y));
        // check if the projectile is out of bounds
    }

    @Override
    public boolean isActive() {
        return alive;
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

    

}
