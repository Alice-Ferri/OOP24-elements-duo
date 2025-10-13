package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionChecker;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;

/* class to manage collisions */

public class CollisionManager {
    private CollisionChecker ck = new CollisionCheckerImpl();

    public void manageCollisions(List<Collidable> entities) {
        List<CollisionInformations> ci = ck.checkCollisions(entities);
        for (CollisionInformations c : ci) {
            Collidable a = c.getObjectA();
            Collidable b = c.getObjectB();
        }
    }
}
