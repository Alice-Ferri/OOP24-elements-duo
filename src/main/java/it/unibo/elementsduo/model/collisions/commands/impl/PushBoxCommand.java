package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.resources.Vector2D;

public class PushBoxCommand implements CollisionCommand {
    private static final double FORCE = 15;
    PushBox box;
    double penetration;
    Vector2D normal;
    boolean playerFirst;

    public PushBoxCommand(PushBox box, double penetration, Vector2D normal, boolean playerFirst){
        this.box = box;
        this.penetration = penetration;
        this.normal = normal;
        this.playerFirst = playerFirst;
    }
    @Override
    public void execute() {
        if (penetration <= 0) return;
        final Vector2D playerNormal = playerFirst ? normal : normal.multiply(-1);
        if (Math.abs(normal.x()) >  Math.abs(normal.y())){
            double direction = -Math.signum(normal.x());
            double push = penetration * FORCE * direction;
            box.push(new Vector2D(push, 0));
            return;
        }
        // vedo se il player è salito sulla cassa
        boolean playerOn = playerNormal.y() < 0;

        if (!playerOn){
            return;
        }

        Vector2D normalBox = playerNormal.multiply(-1);
        box.correctPhysicsCollision(0, normalBox);
    }
}
