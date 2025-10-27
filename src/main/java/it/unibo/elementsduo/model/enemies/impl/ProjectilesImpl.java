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

/**
 * Implementation of the Projectiles interface, representing a moving entity
 * fired by an enemy.
 */
public final class ProjectilesImpl implements Projectiles,Movable {

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
    public void move(final double deltaTime) {
        this.velocity = new Vector2D(deltatime, this.velocity.y());
        this.x += this.velocity.x(); 
    }

    @Override
    public void update(final Set<obstacle> obstacles, final double deltaTime) {
        this.move(obstacles, deltaTime);
    }

    @Override
    public void correctPhysicsCollision(final double penetration, final Vector2D normal) {

    final double POSITION_SLOP = 0.001;
    final double CORRECTION_PERCENT = 0.8;
    if (penetration <= 0) {
    return;
    }
    final double depth = Math.max(penetration - POSITION_SLOP, 0.0);
    final Vector2D correction = normal.multiply(CORRECTION_PERCENT * depth);
    this.x += correction.x();
    this.y += correction.y();

    final double velocityNormal = this.velocity.dot(normal);
    if (velocityNormal < 0) {
        this.velocity = this.velocity.subtract(normal.multiply(velocityNormal));
    }
}
@Override
    public HitBox getHitBox() {
        return new HitBoxImpl(new Position(this.x, this.y), 1, 1);
    }


}

