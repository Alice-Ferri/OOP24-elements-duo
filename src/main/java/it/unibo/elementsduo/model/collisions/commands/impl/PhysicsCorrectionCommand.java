package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.resources.Vector2D;

public class PhysicsCorrectionCommand implements CollisionCommand {

    Movable movable;
    double penetration;
    Vector2D normal;

    public PhysicsCorrectionCommand(Movable movable, double penetration, Vector2D normal) {
        this.movable = movable;
        this.penetration = penetration;
        this.normal = normal;
    }

    @Override
    public void execute() {
        this.movable.correctPhysicsCollision(penetration, normal);
    }

}
