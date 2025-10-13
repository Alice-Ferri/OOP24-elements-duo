package it.unibo.elementsduo.model.enemies.impl;


import java.util.Optional;

import it.unibo.elementsduo.model.enemies.api.EnemiesType;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.obstacles.impl.Floor;
import it.unibo.elementsduo.model.obstacles.impl.Wall;
import it.unibo.elementsduo.model.obstacles.impl.fireExit;
import it.unibo.elementsduo.model.obstacles.impl.fireSpawn;
import it.unibo.elementsduo.model.obstacles.impl.waterExit;
import it.unibo.elementsduo.model.obstacles.impl.waterSpawn;
import it.unibo.elementsduo.utils.Position;

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
    public void move(Level level) {
    double nextX = this.x + direction * speed;
    double y = this.y;

    // Calcola tile davanti e sotto
    int frontX = (int) (direction > 0 ? Math.floor(nextX + 1) : Math.floor(nextX));
    int belowX = (int) (direction > 0 ? Math.floor(nextX + 0.5) : Math.floor(nextX));
    int frontY = (int) Math.floor(y);
    int belowY = (int) Math.floor(y + 1);

    Position frontTile = new Position(frontX, frontY);
    Position belowTile = new Position(belowX, belowY);

    // Controlli di collisione
    boolean wallAhead = isBlocked(level, frontTile);
    boolean noGround = !isBlocked(level, belowTile);

    if (wallAhead || noGround) {
        setDirection(); // gira
    } else {
        x = nextX; // muovi avanti
    }
}

public boolean isBlocked(Level level, Position pos) {
    return level.getAllObstacles().stream()
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

    @Override
    public Optional<Projectiles> attack() {
        // Classic enemy does not attack
        return Optional.empty();
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

    @Override
    public void update(Level level ) {
        move(level);
    }
}
