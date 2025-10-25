package it.unibo.elementsduo.controller.impl;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;

public final class InputController implements KeyEventDispatcher {

    private static final double RUN_SPEED = 8.0;
    private static final double JUMP_STRENGTH = 6.0;

    private final Level level;
    private final EnumMap<PlayerType, DirectionScheme> playerControls = new EnumMap<>(PlayerType.class);

    private final Set<Integer> pressed = new HashSet<>();
    private final Set<Integer> handledPress = new HashSet<>();

    private boolean enabled = true;
    private boolean installed = false;

    public InputController(final Level level) {
        this.level = Objects.requireNonNull(level);

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

    public void update(final double deltaTime) {

        final Collection<Player> players = level.getAllPlayers();
        if (!enabled) {
            return;
        }

        if (players == null || players.isEmpty()) {
            return;
        }

        players.stream()
                .filter(p -> playerControls.get(p.getPlayerType()) != null)
                .forEach(p -> {
                    final DirectionScheme controls = playerControls.get(p.getPlayerType());
                    applyHorizontalMovement(p, controls, deltaTime);
                    applyJump(p, controls);
                });
    }

    private void applyHorizontalMovement(Player p, DirectionScheme controls, double deltaTime) {

        final double velocityX;
        final boolean left = pressed.contains(controls.left);
        final boolean right = pressed.contains(controls.right);

        if (left == right) {
            return;
        }

        velocityX = left ? -RUN_SPEED : RUN_SPEED;
        p.move(velocityX * deltaTime);
    }

    private void applyJump(Player p, DirectionScheme controls) {

        final boolean jumpDown = pressed.contains(controls.jump);

        if (jumpDown && !handledPress.contains(controls.jump)) {
            handledPress.add(controls.jump);
            if (p.isOnGround()) {
                p.jump(JUMP_STRENGTH);
            }
        }
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
