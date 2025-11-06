package it.unibo.elementsduo.model.interactions.core.impl.handlers;

import java.util.HashSet;
import java.util.Set;

import it.unibo.elementsduo.model.interactions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.interactions.core.impl.InteractionResponse;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.api.Toggler;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles collisions between a {@link Player} and a {@link Toggler} such as a
 * lever.
 *
 * <p>
 * This handler triggers a toggle action when a player collides with a lever
 * for the first time during a frame. It ensures that the lever is not toggled
 * repeatedly while the player remains in contact.
 * </p>
 */
public final class LeverActivationHandler extends AbstractInteractionHandler<Player, Toggler> {

    /** Levers interacted with during the current frame. */
    private final Set<Toggler> togglersThisFrame = new HashSet<>();

    /** Levers interacted with during the previous frame. */
    private final Set<Toggler> togglersLastFrame = new HashSet<>();

    /**
     * Creates a new {@code LeverActivationHandler} for handling player–lever
     * collisions.
     */
    public LeverActivationHandler() {
        super(Player.class, Toggler.class);
    }

    /**
     * Handles a collision between a {@link Player} and a {@link Toggler}.
     *
     * <p>
     * Adds the lever to the current frame's set of togglers and issues a toggle
     * command if it was not toggled in the previous frame.
     * </p>
     *
     * @param player  the player interacting with the lever
     * @param trigger the lever being toggled
     * @param c       collision information
     * @param builder the collision response builder
     */
    @Override
    public void handleCollision(final Player player, final Toggler trigger, final CollisionInformations c,
            final InteractionResponse.Builder builder) {

        togglersThisFrame.add(trigger);

        if (!togglersLastFrame.contains(trigger)) {
            builder.addLogicCommand(trigger::toggle);
        }
    }

    /**
     * Clears the current frame's toggler set before collision processing starts.
     */
    @Override
    public void onUpdateStart() {
        togglersThisFrame.clear();
    }

    /** Updates the toggler tracking sets after collision processing ends. */
    @Override
    public void onUpdateEnd() {
        togglersLastFrame.clear();
        togglersLastFrame.addAll(togglersThisFrame);
    }
}
