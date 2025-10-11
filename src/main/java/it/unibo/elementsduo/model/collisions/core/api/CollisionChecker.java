package it.unibo.elementsduo.model.collisions.core.api;

import java.util.List;
import java.util.Optional;

import it.unibo.elementsduo.resources.Vector2D;

public interface CollisionChecker {

    public void checkCollisions(List<Collidable> entities);

    public Optional<Double> getPenetration(Collidable objectA, Collidable objectB);

    public Optional<Vector2D> getNormal(Collidable objectA, Collidable objectB);

}
