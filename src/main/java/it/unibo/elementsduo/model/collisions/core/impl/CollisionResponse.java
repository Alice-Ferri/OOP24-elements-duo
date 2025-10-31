package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;

public class CollisionResponse {
    // colleziono i comandi generati dagli handler in una lista
    private List<CollisionCommand> physicsCommands = new ArrayList<>();
    private List<CollisionCommand> logicCommands = new ArrayList<>();

    private CollisionResponse(List<CollisionCommand> physicsCommands, List<CollisionCommand> logicCommands) {
        this.physicsCommands = List.copyOf(physicsCommands);
        this.logicCommands = List.copyOf(logicCommands);
    }

    public void execute() {
        for (CollisionCommand c : this.physicsCommands) {
            c.execute();
        }

        for (CollisionCommand c : this.logicCommands) {
            c.execute();
        }
    }

    public static class Builder {
        private final List<CollisionCommand> physicsCommands = new ArrayList<>();
        private final List<CollisionCommand> logicCommands = new ArrayList<>();

        public void addLogicCommand(CollisionCommand command) {
            this.logicCommands.add(command);
        }

        public void addPhysicsCommand(CollisionCommand command) {
            this.physicsCommands.add(command);
        }

        public CollisionResponse build() {
            return new CollisionResponse(this.physicsCommands, this.logicCommands);
        }
    }

}
