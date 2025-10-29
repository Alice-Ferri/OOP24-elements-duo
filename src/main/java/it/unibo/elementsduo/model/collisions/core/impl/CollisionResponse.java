package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;

public class CollisionResponse {
    // colleziono i comandi generati dagli handler in una lista
    private List<CollisionCommand> physicsCommands = new ArrayList<>();
    private List<CollisionCommand> logicCommands = new ArrayList<>();

    public void addLogicCommand(CollisionCommand command) {
        this.logicCommands.add(command);
    }

    public void addPhysicsCommand(CollisionCommand command) {
        this.physicsCommands.add(command);
    }

    public void execute() {
        for (CollisionCommand c : this.physicsCommands) {
            c.execute();
        }

        for (CollisionCommand c : this.logicCommands) {
            c.execute();
        }
    }

}
