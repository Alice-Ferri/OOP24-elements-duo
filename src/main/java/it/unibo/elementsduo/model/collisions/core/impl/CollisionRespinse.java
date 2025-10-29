package it.unibo.elementsduo.model.collisions.core.impl;

import it.unibo.elementsduo.model.collisions.commands.CollisionCommand;

import java.util.ArrayList;
import java.util.List;

public class CollisionRespinse {
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
