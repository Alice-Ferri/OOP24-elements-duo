package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.button;
import it.unibo.elementsduo.resources.Position;

/**
 * Factory interface for creating interactive obstacles within the game world.
 * <p>
 * Provides methods to instantiate different types of interactive elements,
 * such as levers, buttons, pushable boxes, and moving platforms.
 * Implementations of this interface define how these obstacles are constructed
 * and initialized.
 */
public interface InteractiveObstacleFactory {
    /**
     * Creates a new {@link Lever} centered at the given position.
     *
     * @param pos the position of the lever's center
     * @return a new {@code Lever} instance
     */
    Lever createLever(Position pos);

    /**
     * Creates a new {@link PushBox} centered at the specified position.
     *
     * @param pos the position of the box's center
     * @return a new {@code PushBox} instance
     */
    PushBox createPushBox(Position pos);

    /**
     * Creates a new moving {@link PlatformImpl} that moves between
     * two target positions.
     *
     * @param pos the initial position of the platform
     * @param a   the first target position
     * @param b   the second target position
     * @return a new {@code PlatformImpl} instance
     */
    PlatformImpl createMovingPlatform(Position pos, Position a, Position b);

    /**
     * Creates a new {@link button} centered at the specified position.
     *
     * @param pos the position of the button's center
     * @return a new {@code button} instance
     */
    button createButton(Position pos);
}
