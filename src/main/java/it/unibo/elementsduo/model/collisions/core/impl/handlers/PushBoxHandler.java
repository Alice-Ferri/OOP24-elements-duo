package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.commands.impl.PushBoxCommand;
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
        collisionResponse.addPhysicsCommand(new PushBoxCommand(box, c.getPenetration(), c.getNormal(), c.getObjectA() instanceof Player));
    }

}
