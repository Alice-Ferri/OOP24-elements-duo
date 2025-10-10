package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

/* class to manage collisions */

public class CollisionManager {
    public void checkCollisions(List<Collidable> entities) {
        for (int i = 0; i < entities.size(); i++) {
            for (int k = i + 1; k < entities.size(); k++) {
                if (entities.get(i).getHitBox().intersects(entities.get(k).getHitBox())) {
                    System.out.println("collision detected");
                }
            }
        }
    }
}
