package it.unibo.elementsduo.model.player;

import it.unibo.elementsduo.controller.inputController.api.InputController;
import it.unibo.elementsduo.controller.inputController.impl.InputState;
import it.unibo.elementsduo.model.player.api.PlayerType;

public class InputControllerTesting implements InputController {
    public boolean moveLeft = false;
    public boolean moveRight = false;
    public boolean jump = false;

    @Override
    public InputState getInputState() {
        var stateMap = new java.util.EnumMap<PlayerType, java.util.Map<InputState.Action, Boolean>>(PlayerType.class);
        for (PlayerType type : PlayerType.values()) {
            stateMap.put(type, java.util.Map.of(
                    InputState.Action.LEFT, moveLeft,
                    InputState.Action.RIGHT, moveRight,
                    InputState.Action.JUMP, jump
            ));
        }
        return new InputState(stateMap);
    }

    @Override
    public void markJumpHandled(PlayerType type) {
        jump = false;
    }
    @Override public void install() {

    }
    @Override public void uninstall() {

    }
    @Override public void setEnabled(boolean enabled) {

    }
    @Override public boolean isEnabled() {
        return true;
    }
}