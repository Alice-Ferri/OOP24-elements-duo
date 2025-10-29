package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.button;

public class ButtonActivationCommand implements CollisionCommand {

    private final button b;

    public ButtonActivationCommand(button b) {
        this.b = b;
    }

    @Override
    public void execute() {
        this.b.activate();
    }

}
