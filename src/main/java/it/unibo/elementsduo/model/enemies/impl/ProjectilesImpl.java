package it.unibo.elementsduo.model.enemies.impl;

import java.util.Set;

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
 * Implementation of the Projectiles interface, representing a moving entity
 * fired by an enemy.
 */
public final class ProjectilesImpl implements Projectiles {

    // Campi e static final in ordine corretto, una dichiarazione per riga
    private static final double SPEED = 0.08;
    private double x;
    private double y;
    private final int direction;
    private boolean alive = true;

    /**
     * Constructs a new projectile with an initial position and direction.
     *
     * @param pos the starting position of the projectile.
     * @param direction the initial direction of travel (e.g., 1 or -1).
     */
    public ProjectilesImpl(final Position pos, final int direction) {
        this.x = pos.x();
        this.y = pos.y();
        this.direction = direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Set<Obstacle> obstacles, final double deltaTime) {
        move(obstacles, deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
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
    public void move(final Set<Obstacle> obstacles, final double deltaTime) {
        final double stepX = this.direction * SPEED * deltaTime;
        final double nextX = this.x + stepX;
        final double currentY = this.y; // 'y' era un 'hidden field', rinominato in 'currentY'

        final int frontX = (int) (this.direction > 0 ? Math.floor(nextX + 1) : Math.floor(nextX));
        final int frontY = (int) Math.floor(currentY);

        final Position frontTile = new Position(frontX, frontY);

        final boolean wallAhead = isBlocked(obstacles, frontTile);

        if (wallAhead) {
            this.alive = false; // Il proiettile si disattiva dopo l'impatto.
        } else {
            this.x = nextX; // Esegue il movimento
        }
    }

    /**
     * Checks if the target position is blocked by an obstacle that should stop the projectile.
     *
     * @param obstacles the set of obstacles in the game.
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
}

