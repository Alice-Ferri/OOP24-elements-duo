package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.ProjectileSolidEvent;
import it.unibo.elementsduo.model.enemies.api.Projectiles;

public class ProjectileSolidCommand implements CollisionCommand {

    private final Projectiles projectile;
    private final EventManager eventManager;

    public ProjectileSolidCommand(Projectiles projectile, EventManager eventManager) {
        this.projectile = projectile;
        this.eventManager = eventManager;
    }

    @Override
    public void execute() {
        projectile.deactivate();
        this.eventManager.notify(new ProjectileSolidEvent(projectile));
    }

}
