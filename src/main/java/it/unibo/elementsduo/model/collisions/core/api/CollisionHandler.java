package it.unibo.elementsduo.model.collisions.core.api;

import it.unibo.elementsduo.model.collisions.core.impl.CollisionRespinse;

public interface CollisionHandler {
    boolean canHandle(Collidable a, Collidable b);

    void handle(CollisionInformations c, CollisionRespinse collisionResponse);

    default void onUpdateStart() {
    }

    default void onUpdateEnd() {
    }
}
