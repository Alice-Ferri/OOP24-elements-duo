package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionChecker;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.ButtonActivationHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.LeverActivationHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PhysicsHanlder;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PlayerEnemyHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PushBoxHandler;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.events.impl.EventManager;
import it.unibo.elementsduo.model.player.api.Player;

/* class to manage collisions */

public class CollisionManager {
    private CollisionChecker ck = new CollisionCheckerImpl();
    private CollisionHandlersRegister register = new CollisionHandlersRegister();

    private Set<Lever> LeversColliding = new HashSet<>();
    private Set<Lever> LeverLast = new HashSet<>();

    private final EventManager eventManager;

    public CollisionManager(final EventManager eventManager) {
        this.eventManager = eventManager;
        register.registerHandler(new LeverActivationHandler());
        register.registerHandler(new ButtonActivationHandler());
        register.registerHandler(new PushBoxHandler());
        register.registerHandler(new PlayerEnemyHandler(this.eventManager));
        register.registerHandler(new GemCollisionHandler(this.eventManager));
        register.registerHandler(new PhysicsHanlder());
    }

    public void manageCollisions(List<Collidable> entities) {
        List<CollisionInformations> collisionsInfo = ck.checkCollisions(entities);

        LeversColliding.clear();

        for (CollisionInformations c : collisionsInfo) {
            register.handle(c);

            Collidable a = c.getObjectA();
            Collidable b = c.getObjectB();
            if ((a instanceof Player && b instanceof Lever) || (b instanceof Player && a instanceof Lever)) {
                Lever l = (Lever) (a instanceof Lever ? a : b);
                LeversColliding.add(l);
            }
        }

        Optional<LeverActivationHandler> leverHandlerOptional = register.getHandler(LeverActivationHandler.class);
        LeverActivationHandler leverhandler = null;
        if (leverHandlerOptional.isPresent())
            leverhandler = leverHandlerOptional.get();

        for (Lever l : LeverLast) {
            if (!LeversColliding.contains(l)) {
                leverhandler.atEndCollision(l);
            }
        }

        LeverLast.clear();
        LeverLast.addAll(LeversColliding);

    }

}
