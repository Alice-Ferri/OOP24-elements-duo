package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionChecker;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

/* class to manage collisions */

public class CollisionManager {
    private CollisionChecker ck = new CollisionCheckerImpl();

    public void manageCollisions(List<Collidable> entities) {
        List<CollisionInformations> ci = ck.checkCollisions(entities);
        for (CollisionInformations c : ci) {
            Player player = null;
            Collidable a = c.getObjectA();
            Collidable b = c.getObjectB();
            Vector2D normal = c.getNormal();

            if (a instanceof Player p) {
                player = p;
            }
            if (b instanceof Player p) {
                player = p;
                normal = new Vector2D(-normal.getX(), -normal.getY());
            }

            if (player == null)
                continue;

            final double corrX = normal.getX() * c.getPenetration();
            final double corrY = normal.getY() * c.getPenetration();

            // orizzontal correction
            player.move(corrX);
            // vertical correction
            if (normal.getY() == 1) {
                player.landOn(player.getY() + corrY);
            }
            if (normal.getY() == -1) {
                player.stopJump(player.getY() + corrY);
            }
        }
    }
}
