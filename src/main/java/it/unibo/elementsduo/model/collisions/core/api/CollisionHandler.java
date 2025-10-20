package it.unibo.elementsduo.model.collisions.core.api;

public interface CollisionHandler {
    boolean canHandle(Collidable a, Collidable b);

    void handle(CollisionInformations c);
}
