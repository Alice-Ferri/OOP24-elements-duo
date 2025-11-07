package it.unibo.elementsduo.model.interactions.core.impl.handlers;

import java.util.HashSet;
import java.util.Set;

import it.unibo.elementsduo.model.interactions.core.impl.InteractionResponse;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionInformations;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.api.Pressable;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles interactions between {@link Player} objects and {@link Pressable}
 * elements
 * such as buttons.
 *
 * <p>
 * This handler detects when a player steps on or leaves a button.
 * 
 */
public final class ButtonActivationHandler extends AbstractInteractionHandler<Player, Pressable> {

    /** Buttons currently pressed during this frame. */
    private final Set<Pressable> pressablesThisFrame = new HashSet<>();

    /** Buttons that were pressed in the previous frame. */
    private final Set<Pressable> pressablesLastFrame = new HashSet<>();

    /**
     * Creates a new {@code ButtonActivationHandler} for handling player–button
     * interactions.
     */
    public ButtonActivationHandler() {
        super(Player.class, Pressable.class);
    }

    /**
     * Handles the interaction between a {@link Player} and a {@link Pressable}.
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
    public void handleInteraction(final Player player, final Pressable b, final CollisionInformations c,
            final InteractionResponse.Builder builder) {

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
