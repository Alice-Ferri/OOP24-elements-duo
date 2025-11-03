package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Toggler;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles collisions between a {@link Player} and {@link Toggler} objects
 * such as levers.
 * <p>
 * This handler detects when a player interacts with a toggler object and
 * triggers its toggle behavior when first touched. The toggler is not
 * repeatedly toggled while the player remains in contact.
 * </p>
 */
public final class LeverActivationHandler extends AbstractCollisionHandler<Player, Toggler> {

    /** List of toggler objects currently interacted with this frame. */
    private final List<Toggler> togglersThisFrame = new ArrayList<>();

    /** List of toggler objects interacted with in the previous frame. */
    private final List<Toggler> togglersLastFrame = new ArrayList<>();

    /**
     * Creates a new {@code LeverActivationHandler} to manage collisions between
     * {@link Player} and {@link Toggler} entities.
     */
    public LeverActivationHandler() {
        super(Player.class, Toggler.class);
    }

    /**
     * Handles a collision between a {@link Player} and a {@link Toggler}.
     * <p>
     * When a player collides with a toggler, it is marked as interacted with for
     * this frame. If it was not toggled in the previous frame, the toggle command
     * is triggered once.
     * </p>
     *
     * @param player  the player involved in the collision
     * @param trigger the toggler object being collided with
     * @param c       the collision information
     * @param builder the collision response builder used to add logic commands
     */
    @Override
    public void handleCollision(final Player player, final Toggler trigger, final CollisionInformations c,
            final CollisionResponse.Builder builder) {

        togglersThisFrame.add(trigger);

        if (!togglersLastFrame.contains(trigger)) {
            builder.addLogicCommand(trigger::toggle);
        }
    }

    /**
     * Called at the start of each update frame to reset the list of togglers
     * currently interacted with.
     */
    @Override
    public void onUpdateStart() {
        togglersThisFrame.clear();
    }

    /**
     * Called at the end of each update frame to update the list of togglers
     * that remain interacted with.
     */
    @Override
    public void onUpdateEnd() {
        togglersLastFrame.clear();
        togglersLastFrame.addAll(togglersThisFrame);
    }
}
