package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles collisions between {@link Player} objects and {@link Lever}
 * instances.
 * 
 * <p>
 * When a player collides with a lever, this handler toggles its activation
 * state.
 * The lever is only toggled once per contact, avoiding repeated activations
 * while the player remains in collision with it.
 */
public final class LeverActivationHandler extends AbstractCollisionHandler<Player, Lever> {

    private final List<Lever> leversThisFrame = new ArrayList<>();
    private final List<Lever> leversLastFrame = new ArrayList<>();

    /**
     * Creates a new {@code LeverActivationHandler} for handling player–lever
     * collisions.
     */
    public LeverActivationHandler() {
        super(Player.class, Lever.class);
    }

    /**
     * Handles a collision between a {@link Player} and a {@link Lever}.
     * 
     * <p>
     * If the lever was not in contact during the previous frame, it is toggled.
     *
     * @param player  the player involved in the collision
     * @param trigger the lever involved in the collision
     * @param c       the collision information
     * @param builder the collision response builder used to queue logic commands
     */
    @Override
    public void handleCollision(final Player player, final Lever trigger, final CollisionInformations c,
            final CollisionResponse.Builder builder) {
        leversThisFrame.add(trigger);

        if (!leversLastFrame.contains(trigger)) {
            builder.addLogicCommand(trigger::toggle);
        }
    }

    /**
     * Called at the beginning of each update cycle.
     * 
     * <p>
     * Clears the list of levers currently in contact for the new frame.
     */
    public void onUpdateStart() {
        leversThisFrame.clear();
    }

    /**
     * Called at the end of each update cycle.
     * 
     * <p>
     * Updates the list of levers that were in contact during the current frame,
     * preparing for the next collision check.
     */
    public void onUpdateEnd() {
        leversLastFrame.clear();
        leversLastFrame.addAll(leversThisFrame);
    }
}
