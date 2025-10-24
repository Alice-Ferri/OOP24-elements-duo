package it.unibo.elementsduo.model.enemies.impl;

import java.util.Optional;
import java.util.Set;

import it.unibo.elementsduo.model.enemies.api.EnemiesType;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Obstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.fireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.waterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.fireSpawn;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.waterSpawn;
import it.unibo.elementsduo.resources.Position;

/**
 * Standard enemy that moves laterally in the level and inflicts damage when the player touches it.
 */
public final class ClassicEnemiesImpl implements Enemy {


    private static final double SPEED = 0.1;


    private boolean alive;
    private double x;
    private final double y;
    private int direction = 1;

    /**
     * Constructor for the classic enemy.
     * @param pos the starting position.
     */
    public ClassicEnemiesImpl(final Position pos) {
        this.x = pos.x();
        this.y = pos.y();
        this.alive = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final Set<Obstacle> obstacles, final double deltaTime) {
        final double stepX = this.direction * SPEED * deltaTime;
        final double nextX = this.x + stepX;
        final double currentY = this.y;

        final int frontX = (int) (this.direction > 0 ? Math.floor(nextX + 1) : Math.floor(nextX));
        final int belowX = (int) (this.direction > 0 ? Math.floor(nextX + 0.5) : Math.floor(nextX));
        final int frontY = (int) Math.floor(currentY);
        final int belowY = (int) Math.floor(currentY + 1);

        final Position frontTile = new Position(frontX, frontY);
        final Position belowTile = new Position(belowX, belowY);


        final boolean wallAhead = this.isBlocked(obstacles, frontTile);
        final boolean noGround = !this.isBlocked(obstacles, belowTile);

        if (wallAhead || noGround) {
            this.setDirection();
        } else {
            this.x = nextX;
        }
    }

    /**
     * @param obstacles the set of obstacles.
     * @param pos the position to check.
     * @return true if the position is blocked, false otherwise.
     */
    private boolean isBlocked(final Set<Obstacle> obstacles, final Position pos) {
        return obstacles.stream()
            .filter(ob -> ob.getPos().equals(pos))
            .anyMatch(ob ->
                ob instanceof Wall
                || ob instanceof Floor
                || ob instanceof fireSpawn
                || ob instanceof waterSpawn
                || ob instanceof fireExit
                || ob instanceof waterExit
            );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Projectiles> attack() {

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getX() { 
        return this.x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getY() {
        return this.y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDirection() {
        return this.direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDirection() {
        this.direction *= -1;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Set<Obstacle> obstacles, final double deltaTime) {
        this.move(obstacles, deltaTime);
    }
}

