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
    public boolean isActionPressed(final PlayerType player, final Action action) {
        return Optional.ofNullable(this.state.get(player))
                       .map(m -> m.getOrDefault(action, false))
                       .orElse(false);
    }

    /**
     * Returns an immutable copy of the complete state.
     *
     * @return a map of key states for each {@link PlayerType}, with boolean values for each {@link Action}
     */
    public Map<PlayerType, Map<Action, Boolean>> getState() {
        final EnumMap<PlayerType, Map<Action, Boolean>> copy = new EnumMap<>(PlayerType.class);
        this.state.forEach((p, m) -> copy.put(p, Map.copyOf(m)));
        return copy;
    }

    /**
     * Enum of actions that can be pressed by players.
     */
    public enum Action {
        LEFT,
        RIGHT,
        JUMP
    }
}
