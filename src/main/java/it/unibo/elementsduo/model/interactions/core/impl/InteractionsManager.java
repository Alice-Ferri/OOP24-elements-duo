package it.unibo.elementsduo.model.interactions.core.impl;

import java.util.List;

import it.unibo.elementsduo.model.interactions.core.api.Collidable;
import it.unibo.elementsduo.model.interactions.core.impl.handlers.ButtonActivationHandler;
import it.unibo.elementsduo.model.interactions.core.impl.handlers.GemInteractionHandler;
import it.unibo.elementsduo.model.interactions.core.impl.handlers.LeverActivationHandler;
import it.unibo.elementsduo.model.interactions.core.impl.handlers.PhysicsHandler;
import it.unibo.elementsduo.model.interactions.core.impl.handlers.PlayerEnemyHandler;
import it.unibo.elementsduo.model.interactions.core.impl.handlers.PlayerExitHandler;
import it.unibo.elementsduo.model.interactions.core.impl.handlers.PlayerHazardHandler;
import it.unibo.elementsduo.model.interactions.core.impl.handlers.PlayerPlatformHandler;
import it.unibo.elementsduo.model.interactions.core.impl.handlers.PlayerProjectileHandler;
import it.unibo.elementsduo.model.interactions.core.impl.handlers.ProjectileSolidHandler;
import it.unibo.elementsduo.model.interactions.core.impl.handlers.PlayerPushBoxHandler;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionChecker;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionInformations;
import it.unibo.elementsduo.model.interactions.detection.impl.CollisionCheckerImpl;
import it.unibo.elementsduo.model.interactions.events.impl.EventManager;

/**
 * Manages all interactions checks and responses between collidable entities in
 * the
 * game world.
 * 
 * <p>
 * The {@code InteractionManager} uses a {@link CollisionChecker} to detect
 * collisions,
 * delegates handling to registered interactions handlers, and executes the
 * resulting
 * interaction responses.
 */
public final class InteractionsManager {

    private final CollisionChecker ck = new CollisionCheckerImpl();
    private final InteractionsHandlersRegister register = new InteractionsHandlersRegister();
    private final EventManager eventManager;

    /**
     * Creates a new {@code InteractionManager} and registers all interaction
     * handlers.
     *
     * @param eventManager the event manager used to dispatch events on the game
     * 
     */
    public InteractionsManager(final EventManager eventManager) {
        this.eventManager = eventManager;
        register.registerHandler(new LeverActivationHandler());
        register.registerHandler(new ButtonActivationHandler());
        register.registerHandler(new PlayerPushBoxHandler());
        register.registerHandler(new PlayerEnemyHandler(this.eventManager));
        register.registerHandler(new GemInteractionHandler(this.eventManager));
        register.registerHandler(new ProjectileSolidHandler(this.eventManager));
        register.registerHandler(new PlayerHazardHandler(this.eventManager));
        register.registerHandler(new PlayerProjectileHandler(eventManager));
        register.registerHandler(new PhysicsHandler());
        register.registerHandler(new PlayerExitHandler(this.eventManager));
        register.registerHandler(new PlayerPlatformHandler());
    }

    /**
     * Performs collision detection and handling for all given collidable entities.
     * 
     * <p>
     * The method checks for collisions among the provided entities,
     * delegates collision handling to the registered handlers, and executes the
     * final
     * collision responses.
     * 
     * <p>
     * This method is not designed for overriding. If customization is required,
     * prefer
     * extending {@link InteractionsHandlersRegister} or individual handlers.
     *
     * @param entities the list of collidable entities to check for collisions
     */
    public void manageInteractions(final List<Collidable> entities) {
        register.notifyUpdateStart();

        final List<CollisionInformations> collisionsInfo = ck.checkCollisions(entities);
        final InteractionResponse.Builder builder = new InteractionResponse.Builder();

        collisionsInfo.forEach(c -> register.handle(c, builder));

        register.notifyUpdateEnd();

        final InteractionResponse response = builder.build();
        response.execute();
    }
}
