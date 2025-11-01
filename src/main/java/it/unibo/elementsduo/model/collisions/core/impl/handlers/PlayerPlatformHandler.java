package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public final class PlayerPlatformHandler extends AbstractCollisionHandler<Player, PlatformImpl> {

    private static final double VERTICAL_THRESHOLD = -0.5;

    public PlayerPlatformHandler() {
        super(Player.class, PlatformImpl.class);
    }

    @Override
    public void handleCollision(final Player player, final PlatformImpl platform,
            final CollisionInformations c, final CollisionResponse.Builder builder) {

        final Vector2D playerNormal = getNormalFromPerspective(player, c);

        if (playerNormal.y() < VERTICAL_THRESHOLD) {
            builder.addLogicCommand(() -> {
                player.setVelocityY(platform.getVelocity().y());
            });
        }
    }
}