package it.unibo.elementsduo.model.enemies.impl;

import java.util.Set;

import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.obstacles.impl.Floor;
import it.unibo.elementsduo.model.obstacles.impl.Wall;
import it.unibo.elementsduo.utils.Position;

public class ProjectilesImpl implements Projectiles {
    private double x, y;
    private static final double SPEED = 0.05;
    private int direction;
    private boolean alive = true;

    public ProjectilesImpl(Position pos, int direction) {
        this.x = pos.x();
        this.y = pos.y();
        this.direction = direction;
    }
    @Override
    public void update(Set<obstacle> obstacles, double deltaTime) {
        move(obstacles,deltaTime);
    
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

    @Override
    public void move(Set<obstacle> obstacles, double deltaTime) {
        double stepX = direction * SPEED * deltaTime;
        x += stepX;

        Position pos = new Position((int) Math.floor(x), (int) Math.floor(y));
        boolean blocked = isBlocked(obstacles, pos);

        if (blocked) {
            alive = false; 
        }
    }

    public boolean isBlocked(Set<obstacle> obstacles, Position pos) {
        return obstacles.stream()
            .filter(ob -> ob.getPos().equals(pos))
            .anyMatch(ob -> 
                ob instanceof Wall ||
                ob instanceof Floor
            );
    }
}
