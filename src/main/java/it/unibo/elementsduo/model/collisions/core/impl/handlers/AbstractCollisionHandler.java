package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;

public abstract class AbstractCollisionHandler<T1 extends Collidable, T2 extends Collidable>
        implements CollisionHandler {
    private Class<T1> typeA;
    private Class<T2> typeB;

    public AbstractCollisionHandler(Class<T1> typeA, Class<T2> typeB) {
        this.typeA = typeA;
        this.typeB = typeB;
    }

    public boolean canHandle(Collidable a, Collidable b) {
        return (typeA.isInstance(a) && typeB.isInstance(b)) || (typeA.isInstance(b) && typeB.isInstance(a));
    }

    public void handle(CollisionInformations c, CollisionResponse collisionResponse) {
        Collidable a = c.getObjectA();
        Collidable b = c.getObjectB();
        if (typeA.isInstance(a) && typeB.isInstance(b)) {
            this.handleCollision(typeA.cast(a), typeB.cast(b), c, collisionResponse);
        } else if (typeA.isInstance(b) && typeB.isInstance(a)) {
            this.handleCollision(typeA.cast(b), typeB.cast(a), c, collisionResponse);
        }
    }

    protected abstract void handleCollision(T1 a, T2 b, CollisionInformations c, CollisionResponse collisionResponse);
}
