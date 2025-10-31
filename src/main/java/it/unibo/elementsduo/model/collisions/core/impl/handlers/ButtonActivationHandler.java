package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.button;

/**
 * Handles collisions between {@link Player} objects and {@link button}
 * instances.
 * 
 * <p>
 * When a player collides with a button, this handler activates the button.
 * When the player moves away, the button is deactivated at the end of the
 * update cycle.
 */
public final class ButtonActivationHandler extends AbstractCollisionHandler<Player, button> {

    private final List<button> buttonsThisFrame = new ArrayList<>();
    private final List<button> buttonsLastFrame = new ArrayList<>();

    /**
     * Creates a new {@code ButtonActivationHandler} for
     * {@link Player}-{@link button} collisions.
     */
    public ButtonActivationHandler() {
        super(Player.class, button.class);
    }

    /**
     * Handles the collision between a player and a button.
     * 
     * <p>
     * If the button was not pressed in the previous frame, a logic command is
     * queued
     * to activate it.
     *
     * @param player  the player involved in the collision
     * @param b       the button involved in the collision
     * @param c       the collision information
     * @param builder the collision response builder used to queue activation
     *                commands
     */
    @Override
    public void handleCollision(final Player player, final button b, final CollisionInformations c,
            final CollisionResponse.Builder builder) {
        buttonsThisFrame.add(b);
        if (!buttonsLastFrame.contains(b)) {
            builder.addLogicCommand(b::activate);
        }
    }

    /**
     * Called at the beginning of each update cycle.
     * 
     * <p>
     * Clears the list of buttons detected in the current frame.
     */
    public void onUpdateStart() {
        buttonsThisFrame.clear();
    }

    /**
     * Called at the end of each update cycle.
     * 
     * <p>
     * Deactivates any buttons that were pressed in the previous frame but
     * not in the current one, ensuring proper button state management.
     */
    public void onUpdateEnd() {
        buttonsLastFrame.stream()
                .filter(b -> !buttonsThisFrame.contains(b))
                .forEach(button::deactivate);

        buttonsLastFrame.clear();
        buttonsLastFrame.addAll(buttonsThisFrame);
    }
}
