package it.unibo.elementsduo.model.enemies.impl;

import java.util.Set;

import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.obstacles.impl.Floor;
import it.unibo.elementsduo.model.obstacles.impl.Wall;
import it.unibo.elementsduo.model.obstacles.impl.fireExit;
import it.unibo.elementsduo.model.obstacles.impl.fireSpawn;
import it.unibo.elementsduo.model.obstacles.impl.waterExit;
import it.unibo.elementsduo.model.obstacles.impl.waterSpawn;
import it.unibo.elementsduo.utils.Position;

public class ProjectilesImpl implements Projectiles {
    private double x, y;
    private static final double SPEED = 0.08;
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
        double stepX = direction * SPEED* deltaTime; 
    double nextX = this.x + stepX;
    double y = this.y;


    int frontX = (int) (direction > 0 ? Math.floor(nextX + 1) : Math.floor(nextX));
    int frontY = (int) Math.floor(y);

    Position frontTile = new Position(frontX, frontY);

    
    boolean wallAhead = isBlocked(obstacles, frontTile);

    if (wallAhead) {
        this.alive=false; // Cambia direzione
    } else {
        x = nextX; // Esegue il movimento
    }
    }

    public boolean isBlocked(Set<obstacle> obstacles, Position pos) {
        return obstacles.stream()
            .filter(ob -> ob.getPos().equals(pos))
            .anyMatch(ob -> 
                ob instanceof Wall ||
                ob instanceof Floor ||
                ob instanceof fireSpawn ||
            ob instanceof waterSpawn ||
            ob instanceof fireExit ||
            ob instanceof waterExit
            );
    }
}
