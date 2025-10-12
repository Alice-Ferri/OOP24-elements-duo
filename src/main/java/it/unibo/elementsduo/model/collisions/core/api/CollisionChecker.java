package it.unibo.elementsduo.model.collisions.core.api;

import java.util.List;

public interface CollisionChecker {

    public List<CollisionInformations> checkCollisions(List<Collidable> entities);

}
