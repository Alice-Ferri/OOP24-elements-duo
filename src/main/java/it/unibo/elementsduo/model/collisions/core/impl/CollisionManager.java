package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionChecker;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.ButtonActivationHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.LeverActivationHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PhysicsHanlder;

/* class to manage collisions */

public class CollisionManager {
    private CollisionChecker ck = new CollisionCheckerImpl();
    private CollisionHandlersRegister register;

    public CollisionManager() {
        register = new CollisionHandlersRegister();
        register.registerHandler(new LeverActivationHandler());
        register.registerHandler(new ButtonActivationHandler());
        register.registerHandler(new PhysicsHanlder());
    }

    public void manageCollisions(List<Collidable> entities) {
        List<CollisionInformations> collisionsInfo = ck.checkCollisions(entities);

        for (CollisionInformations c : collisionsInfo) {
            register.handle(c);
        }
    }

}
