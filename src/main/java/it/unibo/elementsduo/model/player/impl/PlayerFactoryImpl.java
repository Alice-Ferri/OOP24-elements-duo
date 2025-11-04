package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerFactory;
import it.unibo.elementsduo.model.player.api.handlers.PlayerCollisionHandler;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.api.handlers.PlayerInputHandler;
import it.unibo.elementsduo.model.player.api.handlers.PlayerPhysicsHandler;
import it.unibo.elementsduo.model.player.impl.handlers.PlayerCollisionHandlerImpl;
import it.unibo.elementsduo.model.player.impl.handlers.PlayerInputHandlerImpl;
import it.unibo.elementsduo.model.player.impl.handlers.PlayerPhysicsHandlerImpl;
import it.unibo.elementsduo.resources.Position;

/**
 * Factory class responsible for creating concrete Player instances
 * with correctly configured handler dependencies.
 */
public final class PlayerFactoryImpl implements PlayerFactory {

    public PlayerFactoryImpl() { }


    /**
     * Generic factory method that creates a Player based on the given type.
     *
     * @param type the player type (FIREBOY or WATERGIRL)
     * @param startPos the starting position of the player
     * @return a fully initialized Player instance
     * @throws IllegalArgumentException if the type is not supported
     */
    @Override
    public Player createPlayer(final PlayerType playerType, final Position startPos) {
        return switch (playerType) {
            case FIREBOY -> createFireboy(startPos);
            case WATERGIRL -> createWatergirl(startPos);
            default -> throw new IllegalArgumentException("Unsupported player type: " + playerType);
        };
    }

    /**
     * Creates a new Fireboy instance with properly composed handlers.
     *
     * @param startPos the starting position of the player
     * @return a fully initialized Fireboy player
     */
    public static Player createFireboy(final Position startPos) {
        final PlayerPhysicsHandler physicsHandler = new PlayerPhysicsHandlerImpl();
        final PlayerInputHandler inputHandler = new PlayerInputHandlerImpl(physicsHandler);
        final PlayerCollisionHandler collisionHandler = new PlayerCollisionHandlerImpl();

        return new Fireboy(startPos, physicsHandler, inputHandler, collisionHandler);
    }

    /**
     * Creates a new Watergirl instance with properly composed handlers.
     *
     * @param startPos the starting position of the player
     * @return a fully initialized Watergirl player
     */
    public static Player createWatergirl(final Position startPos) {
        final PlayerPhysicsHandler physicsHandler = new PlayerPhysicsHandlerImpl();
        final PlayerInputHandler inputHandler = new PlayerInputHandlerImpl(physicsHandler);
        final PlayerCollisionHandler collisionHandler = new PlayerCollisionHandlerImpl();

        return new Watergirl(startPos, physicsHandler, inputHandler, collisionHandler);
    }
}
