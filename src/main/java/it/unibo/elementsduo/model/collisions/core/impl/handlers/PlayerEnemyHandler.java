package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerPoweredUp;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Handles collisions between a {@link Player} and an {@link Enemy}.
 * 
 * <p>
 * Determines whether the player hits the enemy from above or from another
 * direction and triggers the appropriate response using a
 * {@link PlayerEnemyCommand}.
 */
public final class PlayerEnemyHandler extends AbstractCollisionHandler<Player, Enemy> {

    private static final double VERTICAL_THRESHOLD = -0.5;
    private final EventManager eventManager;

    /**
     * Creates a new {@code PlayerEnemyHandler} for managing player–enemy
     * collisions.
     *
     * @param em the event manager used to notify game events
     */
    public PlayerEnemyHandler(final EventManager em) {
        super(Player.class, Enemy.class);
        this.eventManager = em;
    }

    /**
     * Handles the collision between a {@link Player} and an {@link Enemy}.
     * 
     * <p>
     * Determines whether the player struck the enemy from above and issues
     * a {@link PlayerEnemyCommand} to handle the interaction accordingly.
     *
     * @param player  the player involved in the collision
     * @param enemy   the enemy involved in the collision
     * @param c       the collision information
     * @param builder the collision response builder used to queue logic commands
     */
    @Override
    public void handleCollision(final Player player, final Enemy enemy, final CollisionInformations c,
            final CollisionResponse.Builder builder) {
        final Vector2D normalEnemyPlayer = getNormalFromPerspective(player, c);
        final boolean isPlayerAboveEnemy = normalEnemyPlayer.y() < VERTICAL_THRESHOLD;

        builder.addLogicCommand(() -> {
            final boolean enemyPowerActive = player instanceof PlayerPoweredUp aware
                    && aware.hasPowerUpEffect(PowerUpType.ENEMY_KILL);
            if (enemyPowerActive || isPlayerAboveEnemy) {
                enemy.die();
                this.eventManager.notify(new EnemyDiedEvent(enemy));
            } else {
                this.eventManager.notify(new PlayerDiedEvent(player));
            }
        });
    }
}
