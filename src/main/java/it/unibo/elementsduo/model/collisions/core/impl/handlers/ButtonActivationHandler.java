package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Pressable;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles collisions between a {@link Player} and {@link Pressable} objects
 * such as buttons.
 * <p>
 * This handler detects when a player steps on or leaves a pressable object
 * and triggers its press or release behavior accordingly.
 * </p>
 * <p>
 * The handler keeps track of pressable objects that were interacted with in the
 * current and previous update frames to determine when presses or releases
 * occur.
 * </p>
 */
public final class ButtonActivationHandler extends AbstractCollisionHandler<Player, Pressable> {

    /** List of pressable objects currently interacted with this frame. */
    private final List<Pressable> pressablesThisFrame = new ArrayList<>();

    /** List of pressable objects interacted with in the previous frame. */
    private final List<Pressable> pressablesLastFrame = new ArrayList<>();

    /**
     * Creates a new {@code ButtonActivationHandler} to manage collisions between
     * {@link Player} and {@link Pressable} entities.
     */
    public ButtonActivationHandler() {
        super(Player.class, Pressable.class);
    }

    /**
     * Handles a collision between a {@link Player} and a {@link Pressable}.
     * <p>
     * When a player collides with a pressable object, it is marked as pressed for
     * this frame. If it wasn’t pressed in the previous frame, the press command is
     * triggered.
     * </p>
     *
     * @param player  the player involved in the collision
     * @param b       the pressable object being collided with
     * @param c       the collision information
     * @param builder the collision response builder used to add logic commands
     */
    @Override
    public void handleCollision(final Player player, final Pressable b, final CollisionInformations c,
            final CollisionResponse.Builder builder) {

        pressablesThisFrame.add(b);

        if (!pressablesLastFrame.contains(b)) {
            builder.addLogicCommand(b::press);
        }
    }

    /**
     * Called at the start of each update frame to reset the current frame's tracked
     * pressable objects.
     */
    @Override
    public void onUpdateStart() {
        pressablesThisFrame.clear();
    }

    /**
     * Called at the end of each update frame to release any pressable objects that
     * are no longer being interacted with.
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
