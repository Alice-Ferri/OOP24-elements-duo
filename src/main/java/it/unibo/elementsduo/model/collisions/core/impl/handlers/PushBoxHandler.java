package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public class PushBoxHandler implements CollisionHandler {

    private final static double PUSH_FORCE = 15;

    private final PhysicsHanlder physics = new PhysicsHanlder();

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return (a instanceof Player && b instanceof PushBox) || (a instanceof PushBox && b instanceof Player);
    }

    @Override
    public void handle(CollisionInformations c) {
        Collidable a = c.getObjectA();
        Collidable b = c.getObjectB();

        PushBox box = null;

        if (a instanceof PushBox) {
            box = (PushBox) a;
        } else {
            box = (PushBox) b;
        }

        if (Math.abs(c.getNormal().x()) > Math.abs(c.getNormal().y())) {
            int sign = 1;
            if (c.getNormal().x() > 0)
                sign = 1;
            else
                sign = -1;
            double push = c.getPenetration() * PUSH_FORCE * sign;
            if (a instanceof PushBox)
                push = -push;
            box.push(new Vector2D(push, 0));
        }
    }

}
