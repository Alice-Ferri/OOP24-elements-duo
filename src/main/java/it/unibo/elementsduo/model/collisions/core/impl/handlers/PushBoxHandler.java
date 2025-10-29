package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public class PushBoxHandler extends AbstractCollisionHandler<Player, PushBox> {

    private final static double PUSH_FORCE = 15;

    public PushBoxHandler() {
        super(Player.class, PushBox.class);
    }

    @Override
    public void handleCollision(Player player, PushBox box, CollisionInformations c,
            CollisionResponse collisionResponse) {
        if (Math.abs(c.getNormal().x()) > Math.abs(c.getNormal().y())) {
            int sign = 1;
            if (c.getNormal().x() > 0)
                sign = 1;
            else
                sign = -1;
            double push = c.getPenetration() * PUSH_FORCE * sign;
            if (c.getObjectA() instanceof PushBox)
                push = -push;
            box.push(new Vector2D(push, 0));
        }
    }

}
