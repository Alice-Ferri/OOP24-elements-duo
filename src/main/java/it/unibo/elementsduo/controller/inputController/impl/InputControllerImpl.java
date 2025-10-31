package it.unibo.elementsduo.controller.inputController.impl;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

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

    private boolean isMovePressed(PlayerType type, Function<DirectionScheme, Integer> keySelector) {
        return Optional.ofNullable(playerControls.get(type))
                       .map(keySelector)
                       .map(pressed::contains)
                       .orElse(false);
    }

    public boolean isMoveLeftPressed(PlayerType type) {
        return isMovePressed(type, ds -> ds.left);
    }

    public boolean isMoveRightPressed(PlayerType type) {
        return isMovePressed(type, ds -> ds.right);
    }

    public boolean isJumpPressed(PlayerType type) {
        return Optional.ofNullable(playerControls.get(type))
                       .map(ds -> ds.jump)
                       .map(key -> {
                           if (pressed.contains(key) && !handledPress.contains(key)) {
                               handledPress.add(key);
                               return true;
                           }
                           return false;
                       })
                       .orElse(false);
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

    private record DirectionScheme(int left, int right, int jump) {
        
    }
}


