package it.unibo.elementsduo.controller.inputcontroller.impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.elementsduo.model.player.api.PlayerType;

/**
 * Immutable snapshot of the key states for the actions pressed for each player.
 */
public final class InputState {

    private final Map<PlayerType, Map<Action, Boolean>> state;

    /**
     * Constructs an immutable snapshot from an initial state map.
     *
     * @param state the initial map of key states for each player
     */
    public InputState(final Map<PlayerType, Map<Action, Boolean>> state) {
        this.state = new EnumMap<>(PlayerType.class);
        state.forEach((player, actions) -> this.state.put(player, Map.copyOf(actions)));
    }

    /**
     * Checks if a specific action is currently pressed for a player.
     *
     * @param player the player to check
     * @param action the action to verify (LEFT, RIGHT, JUMP)
     * @return if the action is active or not for the player
     */
    private boolean isActionPressed(final PlayerType player, final Action action) {
        return Optional.ofNullable(this.state.get(player))
                       .map(m -> m.getOrDefault(action, false))
                       .orElse(false);
    }

    /**
     * Checks if the LEFT action is pressed for a player.
     *
     * @param playerType the player to check
     * @return true if LEFT is active, false otherwise
     */
    public boolean isLeftPressed(final PlayerType playerType) {
        return isActionPressed(playerType, Action.LEFT);
    }

    /**
     * Checks if the RIGHT action is pressed for a player.
     *
     * @param playerType the player to check
     * @return true if RIGHT is active, false otherwise
     */
    public boolean isRightPressed(final PlayerType playerType) {
        return isActionPressed(playerType, Action.RIGHT);
    }

    /**
     * Checks if the JUMP action is pressed for a player.
     *
     * @param playerType the player to check
     * @return true if JUMP is active, false otherwise
     */
    public boolean isJumpPressed(final PlayerType playerType) {
        return isActionPressed(playerType, Action.JUMP);
    }
}
