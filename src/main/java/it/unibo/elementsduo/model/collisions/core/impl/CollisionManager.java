package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionChecker;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

/* class to manage collisions */

public class CollisionManager {
    private CollisionChecker ck = new CollisionCheckerImpl();

    public void manageCollisions(List<Collidable> entities) {
        List<CollisionInformations> ci = ck.checkCollisions(entities);
        for (CollisionInformations c : ci) {
            Player player = null;
            Collidable other = null;
            Collidable a = c.getObjectA();
            Collidable b = c.getObjectB();
            Vector2D normal = c.getNormal();

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

    }

    private void handlePlayerVsProjectile(CollisionInformations c) {
        System.out.println("il giocatore ha toccato un nemico: GAME OVER");
    }

    private void handleEnemyVsObstacle(CollisionInformations c) {

    }

}
