package it.unibo.elementsduo.doubles;


import it.unibo.elementsduo.controller.inputController.api.InputController;
import it.unibo.elementsduo.model.player.api.PlayerType;


public class DoubleInputController implements InputController {
    public boolean moveLeft, moveRight, jump;

    @Override
    public boolean isMoveLeftPressed(PlayerType type) {
        return moveLeft;
    }

    @Override
    public boolean isMoveRightPressed(PlayerType type) {
        return moveRight;
    }

    @Override
    public boolean isJumpPressed(PlayerType type) { 
        if(jump) { 
            jump = false;
            return true;
        } 
        return false;
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