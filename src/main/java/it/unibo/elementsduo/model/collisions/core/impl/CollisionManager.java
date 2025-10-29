package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionChecker;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.ButtonActivationHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.GemCollisionsHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.LeverActivationHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PhysicsHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PlayerEnemyHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PlayerHazardHandler;
<<<<<<< HEAD
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PlayerProjectileHandler;
=======

import it.unibo.elementsduo.model.collisions.core.impl.handlers.PlayerExitHandler;

>>>>>>> e45e65b248549db1bca64aab0f2ce09f7ec33972
import it.unibo.elementsduo.model.collisions.core.impl.handlers.ProjectileSolidHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PushBoxHandler;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;

/* class to manage collisions */

public class CollisionManager {
    private CollisionChecker ck = new CollisionCheckerImpl();
    private CollisionHandlersRegister register = new CollisionHandlersRegister();

    private final EventManager eventManager;

    public CollisionManager(final EventManager eventManager) {
        this.eventManager = eventManager;
        register.registerHandler(new LeverActivationHandler());
        register.registerHandler(new ButtonActivationHandler());
        register.registerHandler(new PushBoxHandler());
        register.registerHandler(new PlayerEnemyHandler(this.eventManager));
        register.registerHandler(new GemCollisionsHandler(this.eventManager));
        register.registerHandler(new ProjectileSolidHandler(this.eventManager));
        register.registerHandler(new PlayerHazardHandler(this.eventManager));
        register.registerHandler(new PlayerProjectileHandler(eventManager));
        register.registerHandler(new PhysicsHandler());
        register.registerHandler(new PlayerExitHandler(this.eventManager));
    }

    public void manageCollisions(List<Collidable> entities) {

        register.notifyUpdateStart();

        List<CollisionInformations> collisionsInfo = ck.checkCollisions(entities);

        CollisionResponse collisionResponse = new CollisionResponse();

        for (Collidable c : entities) {
            if (c instanceof Player p) {
                p.setAirborne();
            } else if (c instanceof PushBox b) {
                b.setOnGround(false);
            }
        }

        for (CollisionInformations c : collisionsInfo) {
            register.handle(c, collisionResponse);
        }

        register.notifyUpdateEnd();

        collisionResponse.execute();

    }

}
