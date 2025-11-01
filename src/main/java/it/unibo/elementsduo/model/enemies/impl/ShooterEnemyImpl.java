package it.unibo.elementsduo.model.enemies.impl;

import java.util.Optional;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.resources.Position;

/**
 * Implementation of the "Shooter" enemy.
 * Extends AbstractEnemy, inheriting all state, physics,
 * and movement logic.
 * Specializes the behavior by implementing ranged attack
 * logic based on a cooldown.
 */
public final class ShooterEnemyImpl extends AbstractEnemy {

    private static final double MAX_COOLDOWN = 3.0;
    private static final double SPAWN_OFFSET = 0.5;
    private double shootCooldown;

    /**
     * Constructor for the shooter enemy.
     *
     * @param pos the starting position.
     */
    public ShooterEnemyImpl(final Position pos) {
        super(pos);
        this.shootCooldown = MAX_COOLDOWN;
    }

    /**
     * {@inheritDoc}
     * Implementation of the Template Pattern's "hook" method.
     * Manages the shooter's specific logic: updating the
     * attack cooldown.
     */
    @Override
    protected void updateAttack(final double deltaTime) {
        if (this.shootCooldown > 0) {
            this.shootCooldown -= deltaTime;
        } else {
            this.attack();
        }
    }

    /**
     * {@inheritDoc}
     * Overrides the default method from AbstractEnemy.
     * Implements the attack logic: if the cooldown has expired,
     * creates a new projectile and resets the cooldown.
     */
    @Override
    public Optional<Projectiles> attack() {
        if (this.shootCooldown <= 0) {
            this.shootCooldown = MAX_COOLDOWN;

            final Position projPos = new Position(
                    this.getX() + this.getDirection() * SPAWN_OFFSET,
                    this.getY());

            return Optional.of(new ProjectilesImpl(projPos, this.getDirection())); 
        }
        return Optional.empty();
    }
}
