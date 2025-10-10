package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionChecker;

/* class to manage collisions */

public class CollisionManager {
    CollisionChecker ck = new CollisionCheckerImpl();

    public void manageCollisions(List<Collidable> entities) {
        ck.checkCollisions(entities);
    }
}
