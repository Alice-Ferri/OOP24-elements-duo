package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;

public class CollisionResponse {
    // colleziono i comandi generati dagli handler in una lista
    private List<CollisionCommand> commands = new ArrayList<>();

    public void addCommand(CollisionCommand command) {
        this.commands.add(command);
    }

    public void execute() {
        for (CollisionCommand c : commands) {
            c.execute();
        }
    }

}
