package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.HashSet;
import java.util.Set;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Pressable;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles collisions between {@link Player} objects and {@link Pressable}
 * elements
 * such as buttons.
 *
 * <p>
 * This handler detects when a player steps on or leaves a button. When the
 * player
 * first collides with a button, it triggers {@link Pressable#press()}. When the
 * player moves away, it triggers {@link Pressable#release()}.
 * </p>
 */
public final class ButtonActivationHandler extends AbstractCollisionHandler<Player, Pressable> {

    /** Buttons currently pressed during this frame. */
    private final Set<Pressable> pressablesThisFrame = new HashSet<>();

    /** Buttons that were pressed in the previous frame. */
    private final Set<Pressable> pressablesLastFrame = new HashSet<>();

    /**
     * Creates a new {@code ButtonActivationHandler} for handling player–button
     * collisions.
     */
    public ButtonActivationHandler() {
        super(Player.class, Pressable.class);
    }

    /**
     * Handles the collision between a {@link Player} and a {@link Pressable}.
     *
     * <p>
     * Adds the button to the set of currently pressed buttons and triggers
     * {@link Pressable#press()} if it was not pressed in the previous frame.
     * </p>
     *
     * @param player  the player colliding with the button
     * @param b       the button being pressed
     * @param c       collision information
     * @param builder the collision response builder
     */
    @Override
    public void handleCollision(final Player player, final Pressable b, final CollisionInformations c,
            final CollisionResponse.Builder builder) {

        pressablesThisFrame.add(b);

        if (!pressablesLastFrame.contains(b)) {
            builder.addLogicCommand(b::press);
        }
    }

    /** Clears the set of pressed buttons at the start of the update. */
    @Override
    public void onUpdateStart() {
        pressablesThisFrame.clear();
    }

    /**
     * Releases buttons that are no longer being pressed at the end of the update.
     */
    @Override
    public void onUpdateEnd() {
        pressablesLastFrame.stream()
                .filter(b -> !pressablesThisFrame.contains(b))
                .forEach(Pressable::release);

        pressablesLastFrame.clear();
        pressablesLastFrame.addAll(pressablesThisFrame);
    }
}
