package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionChecker;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

/* class to manage collisions */

public class CollisionManager {
    private CollisionChecker ck = new CollisionCheckerImpl();

    public void manageCollisions(List<Collidable> entities) {
        List<CollisionInformations> ci = ck.checkCollisions(entities);
        for (CollisionInformations c : ci) {
            Collidable a = c.getObjectA();
            Collidable b = c.getObjectB();

            if ((a instanceof Player && b instanceof Enemy) || (a instanceof Player && b instanceof Enemy)) {
                handlePlayerVsEnemy(c);
            } else if ((a instanceof Player && b instanceof Projectiles)
                    || (a instanceof Projectiles && b instanceof Player)) {
                handlePlayerVsProjectile(c);
            } else if ((a instanceof Player && b instanceof obstacle)
                    || (a instanceof obstacle && b instanceof Player)) {
                handlePlayerVsWall(c);
            } else if ((a instanceof Enemy && b instanceof obstacle) || (a instanceof obstacle && b instanceof Enemy)) {
                handleEnemyVsObstacle(c);
            }

        }
    }

    private void handlePlayerVsEnemy(CollisionInformations c) {
        System.out.println("il giocatore ha toccato un nemico: GAME OVER");
    }

    private void handlePlayerVsWall(CollisionInformations c) {
        final Player player = getPlayerFrom(c);
        final Vector2D normal = getNormalFor(player, c);
        final double penetration = c.getPenetration();

        final double corrX = normal.getX() * penetration;
        final double corrY = normal.getY() * penetration;

        // Correzione orizzontale
        player.move(corrX);

        // correzione verticale
        if (normal.getY() == 1) {
            player.landOn(player.getY() + corrY);
        } else if (normal.getY() == -1) {
            player.stopJump(player.getY() + corrY);
        }
    }

    private void handlePlayerVsProjectile(CollisionInformations c) {
        System.out.println("il giocatore ha toccato un nemico: GAME OVER");
    }

    private void handleEnemyVsObstacle(CollisionInformations c) {
    }

    private Player getPlayerFrom(CollisionInformations c) {
        return c.getObjectA() instanceof Player ? (Player) c.getObjectA() : (Player) c.getObjectB();
    }

    private Enemy getEnemyFrom(CollisionInformations c) {
        return c.getObjectA() instanceof Enemy ? (Enemy) c.getObjectA() : (Enemy) c.getObjectB();
    }

    private Projectiles getProjectileFrom(CollisionInformations c) {
        return c.getObjectA() instanceof Projectiles ? (Projectiles) c.getObjectA() : (Projectiles) c.getObjectB();
    }

    private Vector2D getNormalFor(Collidable target, CollisionInformations collision) {
        if (collision.getObjectA() == target) {
            return collision.getNormal();
        } else {
            return new Vector2D(-collision.getNormal().getX(), -collision.getNormal().getY()); // Va invertita.
        }
    }

}
