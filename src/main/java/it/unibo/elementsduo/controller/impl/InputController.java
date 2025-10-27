package it.unibo.elementsduo.controller.impl;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;

public final class InputController implements KeyEventDispatcher {

    private final EnumMap<PlayerType, DirectionScheme> playerControls = new EnumMap<>(PlayerType.class);

    private final Set<Integer> pressed = new HashSet<>();
    private final Set<Integer> handledPress = new HashSet<>();


    private boolean enabled = true;
    private boolean installed = false;

    public InputController() {
        playerControls.put(PlayerType.FIREBOY, new DirectionScheme(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W));
        playerControls.put(PlayerType.WATERGIRL,
                new DirectionScheme(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP));
    }

    public void install() {
        if (installed) {
            return;
        }
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
        this.installed = true;
    }

    public void uninstall() {
        if (!installed) {
            return;
        }
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
        this.installed = false;
        pressed.clear();
        handledPress.clear();
    }

    public boolean isMoveLeftPressed(final PlayerType type) {
        final DirectionScheme controls = playerControls.get(type);
        return controls != null && pressed.contains(controls.left);
    }

    public boolean isMoveRightPressed(final PlayerType type) {
        final DirectionScheme controls = playerControls.get(type);
        return controls != null && pressed.contains(controls.right);
    }

    public boolean isJumpPressed(final PlayerType type) {
        final DirectionScheme controls = playerControls.get(type);
        if (controls == null) {
            return false;
        }

        final boolean jumpDown = pressed.contains(controls.jump);

        if (jumpDown && !handledPress.contains(controls.jump)) {
            handledPress.add(controls.jump); 
            return true; 
        }
        return false; 
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (!enabled)
            return false;

        switch (e.getID()) {
            case KeyEvent.KEY_PRESSED -> {
                pressed.add(e.getKeyCode());
            }
            case KeyEvent.KEY_RELEASED -> {
                pressed.remove(e.getKeyCode());
                handledPress.remove(e.getKeyCode());
            }
            default -> {
            }
        }
        return false; 
    }


    private static final class DirectionScheme {
        private final int left, right, jump;

        DirectionScheme(final int left, final int right, final int jump) {
            this.left = left;
            this.right = right;
            this.jump = jump;
        }
    }
}
