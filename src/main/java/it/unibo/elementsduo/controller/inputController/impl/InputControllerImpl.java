package it.unibo.elementsduo.controller.inputController.impl;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.unibo.elementsduo.controller.inputController.api.InputController;
import it.unibo.elementsduo.model.player.api.PlayerType;

public final class InputControllerImpl implements KeyEventDispatcher, InputController {

    private final EnumMap<PlayerType, DirectionScheme> playerControls = new EnumMap<>(PlayerType.class);
    private final Set<Integer> pressed = new HashSet<>();
    private final Set<Integer> handledPress = new HashSet<>();

    private boolean enabled = true;
    private boolean installed = false;

    public InputControllerImpl() {
        playerControls.put(PlayerType.FIREBOY, new DirectionScheme(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W));
        playerControls.put(PlayerType.WATERGIRL, new DirectionScheme(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP));
    }

    public void install() {
        if (installed) {
            return;
        }
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
        installed = true;
    }

    public void uninstall() {
        if (!installed) {
            return;
        }
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
        installed = false;
        pressed.clear();
        handledPress.clear();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (!enabled) {
            return false;
        }

        switch (e.getID()) {
            case KeyEvent.KEY_PRESSED -> pressed.add(e.getKeyCode());
            case KeyEvent.KEY_RELEASED -> {
                pressed.remove(e.getKeyCode());
                handledPress.remove(e.getKeyCode());
            }
        }
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public InputState getInputState() {
        EnumMap<PlayerType, Map<InputState.Action, Boolean>> map = new EnumMap<>(PlayerType.class);
        playerControls.forEach((player, scheme) -> map.put(player, Map.of(
                InputState.Action.LEFT, isPressed(scheme.left),
                InputState.Action.RIGHT, isPressed(scheme.right),
                InputState.Action.JUMP, isJumpPressed(scheme.jump)
        )));
        return new InputState(map);
    }

    private boolean isPressed(int keyScheme) {
        return pressed.contains(keyScheme);
    }
    
    private boolean isJumpPressed(int keyScheme) {
        return pressed.contains(keyScheme) && !handledPress.contains(keyScheme);
    }

    public void markJumpHandled(PlayerType player) {
        DirectionScheme scheme = playerControls.get(player);
        if(scheme != null) handledPress.add(scheme.jump);
    }

    private record DirectionScheme(int left, int right, int jump) {
        
    }
}


