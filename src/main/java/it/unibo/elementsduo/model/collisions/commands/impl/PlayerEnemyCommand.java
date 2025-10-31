package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.collisions.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * A {@link CollisionCommand} that defines the outcome of a collision
 * between a {@link Player} and an {@link Enemy}.
 * <p>
 * If the player collides with an enemy from above, the enemy dies.
 * Otherwise, the player dies.
 */
public final class PlayerEnemyCommand implements CollisionCommand {

    /** The player involved in the collision. */
    private final Player player;

    /** The enemy involved in the collision. */
    private final Enemy enemy;

    /** The event manager responsible for dispatching events. */
    private final EventManager eventManager;

    /** Indicates whether the player is above the enemy. */
    private final boolean isOn;

    /**
     * Constructs a new {@code PlayerEnemyCommand}.
     *
     * @param player       the player involved in the collision
     * @param enemy        the enemy involved in the collision
     * @param eventManager the {@link EventManager} used to trigger game events
     * @param isOn         {@code true} if the player is above the enemy; otherwise
     *                     {@code false}
     */
    public PlayerEnemyCommand(final Player player, final Enemy enemy, final EventManager eventManager,
            final boolean isOn) {
        this.player = player;
        this.enemy = enemy;
        this.eventManager = eventManager;
        this.isOn = isOn;
    }

    /**
     * Executes the command, applying the correct consequence of the collision.
     * <p>
     * If the player is above the enemy, the enemy dies; otherwise, the player dies.
     */
    @Override
    public void execute() {
        if (isOn) {
            enemy.die();
            this.eventManager.notify(new EnemyDiedEvent(enemy));
        } else {
            this.eventManager.notify(new PlayerDiedEvent(player));
        }
    }
}
