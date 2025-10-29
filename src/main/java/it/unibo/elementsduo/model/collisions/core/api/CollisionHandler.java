package it.unibo.elementsduo.model.collisions.core.api;

import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;

public interface CollisionHandler {
    boolean canHandle(Collidable a, Collidable b);

    void handle(CollisionInformations c, CollisionResponse collisionResponse);

    default void onUpdateStart() {
    }

    default void onUpdateEnd() {
    }
}
