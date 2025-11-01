package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.ButtonActivationHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.GemCollisionsHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.LeverActivationHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PhysicsHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PlayerEnemyHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PlayerHazardHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PlayerProjectileHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PlayerExitHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.ProjectileSolidHandler;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PushBoxHandler;
import it.unibo.elementsduo.model.collisions.detection.api.CollisionChecker;
import it.unibo.elementsduo.model.collisions.detection.impl.CollisionCheckerImpl;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Manages all collision checks and responses between collidable entities in the
 * game world.
 * 
 * <p>
 * The {@code CollisionManager} uses a {@link CollisionChecker} to detect
 * collisions,
 * delegates handling to registered collision handlers, and executes the
 * resulting
 * collision responses.
 */
public final class CollisionManager {

    private final CollisionChecker ck = new CollisionCheckerImpl();
    private final CollisionHandlersRegister register = new CollisionHandlersRegister();
    private final EventManager eventManager;

    /**
     * Creates a new {@code CollisionManager} and registers all collision handlers.
     *
     * @param eventManager the event manager used to trigger game events on
     *                     collisions
     */
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

    /**
     * Performs collision detection and handling for all given collidable entities.
     * 
     * <p>
     * The method checks for collisions, resets entity states (e.g., airborne
     * players),
     * delegates collision handling to the registered handlers, and executes the
     * final
     * collision responses.
     * 
     * <p>
     * This method is not designed for overriding. If customization is required,
     * prefer
     * extending {@link CollisionHandlersRegister} or individual handlers.
     *
     * @param entities the list of collidable entities to check for collisions
     */
    public void manageCollisions(final List<Collidable> entities) {
        register.notifyUpdateStart();

        final List<CollisionInformations> collisionsInfo = ck.checkCollisions(entities);
        final CollisionResponse.Builder builder = new CollisionResponse.Builder();

        collisionsInfo.forEach(c -> register.handle(c, builder));

        register.notifyUpdateEnd();

        final CollisionResponse response = builder.build();
        response.execute();
    }
}
