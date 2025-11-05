package it.unibo.elementsduo.controller.inputcontroller.impl;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.unibo.elementsduo.controller.inputcontroller.api.InputController;
import it.unibo.elementsduo.model.player.api.PlayerType;

/**
 * Implementation of InputController that handles keyboard input for multiple players.
 */
public final class InputControllerImpl implements KeyEventDispatcher, InputController {

    private final Map<PlayerType, DirectionScheme> playerControls = new EnumMap<>(PlayerType.class);
    private final Set<Integer> pressed = new HashSet<>();
    private final Set<Integer> handledPress = new HashSet<>();

    private boolean enabled = true;
    private boolean installed;

    /**
     * Creates a new input controller with predefined key mappings for player type.
     */
    public InputControllerImpl() {
        playerControls.put(PlayerType.FIREBOY, new DirectionScheme(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W));
        playerControls.put(PlayerType.WATERGIRL, new DirectionScheme(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP));
    }

    /**
     * Installs this controller as a global key event dispatcher.
     */
    @Override
    public void install() {
        if (installed) {
            return;
        }
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
        installed = true;
    }

    /**
     * Uninstalls this controller and clears all stored key states.
     */
    @Override
    public void uninstall() {
        if (!installed) {
            return;
        }
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
        installed = false;
        pressed.clear();
        handledPress.clear();
    }

    /**
     * Handles key press and release events to update input state.
     *
     * @param e the key event to process
     *
     * @return always false, allowing normal event propagation
     */
    @Override
    public boolean dispatchKeyEvent(final KeyEvent e) {
        if (!enabled) {
            return false;
        }

        switch (e.getID()) {
            case KeyEvent.KEY_PRESSED -> pressed.add(e.getKeyCode());
            case KeyEvent.KEY_RELEASED -> {
                pressed.remove(e.getKeyCode());
                handledPress.remove(e.getKeyCode());
            }
            default -> { }
        }
        return false;
    }

    /**
     * Enables or disables this input controller.
     *
     * @param enabled true to enable, false to disable
     */
    @Override
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Checks whether the controller is currently enabled.
     *
     * @return true if enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Builds and returns the current input for all players.
     *
     * @return a snapshot of current input states
     */
    @Override
    public InputState getInputState() {
        final Map<PlayerType, Map<Action, Boolean>> map = playerControls.entrySet().stream()
            .collect(() -> new EnumMap<>(PlayerType.class),
                     (acc, entry) -> acc.put(entry.getKey(), Map.of(
                             Action.LEFT, isPressed(entry.getValue().left()),
                             Action.RIGHT, isPressed(entry.getValue().right()),
                             Action.JUMP, isJumpPressed(entry.getValue().jump())
                     )),
                     Map::putAll);
        return new InputState(map);
    }

    /**
     * Checks if a given key is currently pressed.
     *
     * @param keyScheme key code to check
     *
     * @return true if pressed, false otherwise
     */
    private boolean isPressed(final int keyScheme) {
        return pressed.contains(keyScheme);
    }

    /**
     * Checks if the jump key is currently pressed.
     *
     * @param keyScheme key code to check
     * @return true if pressed jump, false otherwise
     */
    private boolean isJumpPressed(final int keyScheme) {
        return pressed.contains(keyScheme) && !handledPress.contains(keyScheme);
    }

    /**
     * Marks the jump input for a player as handled,
     * preventing repeated jumps until the key is released.
     *
     * @param playerType the player type whose jump was handled
     */
    @Override
    public void markJumpHandled(final PlayerType playerType) {
        final DirectionScheme scheme = playerControls.get(playerType);
        if (scheme != null) {
            handledPress.add(scheme.jump);
        }
    }

    /**
     * Represents the key bindings for player movement and jump.
     *
     * @param left  key code for left
     *
     * @param right key code for right
     *
     * @param jump  key code for jumping
     */
    private record DirectionScheme(int left, int right, int jump) { }
}


