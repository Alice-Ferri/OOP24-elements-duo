package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;

public class LeverActivationCommand implements CollisionCommand {

    private final Lever lever;

    public LeverActivationCommand(Lever lever) {
        this.lever = lever;
    }

    @Override
    public void execute() {
        this.lever.toggle();
    }

}
