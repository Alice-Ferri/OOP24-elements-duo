package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public class PhysicsCorrectionCommand implements CollisionCommand {

    Movable movable;
    Collidable other;
    double penetration;
    Vector2D normal;

    public PhysicsCorrectionCommand(Movable movable, Collidable other, double penetration, Vector2D normal) {
        this.movable = movable;
        this.penetration = penetration;
        this.normal = normal;
        this.other = other;
    }

    @Override
    public void execute() {
        this.movable.correctPhysicsCollision(penetration, normal, other);
        if (this.movable instanceof Player player && this.other instanceof PlatformImpl platform && normal.y() < -0.5) {
            player.setVelocityY(platform.getVelocity().y());
        }
    }

}
