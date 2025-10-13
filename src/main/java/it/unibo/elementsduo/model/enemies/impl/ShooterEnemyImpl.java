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

public class ShooterEnemyImpl implements Enemy{

    private boolean alive = true;
    private double x;
    private double y;
    private int direction=1;
    private static final double SPEED=0.02; 
    private int shootCooldown; // Counter used to manage automatic shooting
    private static final int MAX_COOLDOWN = 180; // ticks between two shots


    public ShooterEnemyImpl(char c, Position pos) {
        this.x= pos.x();
        this.y= pos.y();
        this.alive = true;

    }

    @Override
    public void move(Level level) {
    double nextX = this.x + direction * SPEED;
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
        setDirection(); 
    } else {
        x = nextX; 
    }
    if (shootCooldown > 0) {
            shootCooldown--;
            
        }
        else {
            attack();
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

   public Optional<Projectiles> attack() {
        if (shootCooldown <= 0) {
            shootCooldown = MAX_COOLDOWN;
            Position pos = new Position((int)this.x+1,(int)this.y);
            return Optional.of(new ProjectilesImpl(pos, this.direction));
        }
        return Optional.empty();
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
    public void update(Level level) {
        move(level);
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
