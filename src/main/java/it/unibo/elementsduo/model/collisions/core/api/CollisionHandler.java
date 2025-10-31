package it.unibo.elementsduo.model.collisions.core.api;

import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse.Builder;

public interface CollisionHandler {
    boolean canHandle(Collidable a, Collidable b);

    void handle(CollisionInformations c, CollisionResponse.Builder builder);

    default void onUpdateStart() {
    }

    default void onUpdateEnd() {
    }
}
