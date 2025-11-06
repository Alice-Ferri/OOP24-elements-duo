package it.unibo.elementsduo.model.obstacles.impl;

import it.unibo.elementsduo.model.obstacles.api.InteractiveObstacleFactory;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.Button;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.PushBox;
import it.unibo.elementsduo.resources.Position;

/**
 * Implementation of the {@link InteractiveObstacleFactory} interface.
 * 
 * <p>
 * This factory provides creation methods for interactive obstacles such as
 * levers, push boxes, moving platforms, and buttons.
 * </p>
 */
public final class InteractiveObstacleFactoryImpl implements InteractiveObstacleFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Lever createLever(final Position pos) {
        return new Lever(pos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PushBox createPushBox(final Position pos) {
        return new PushBox(pos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlatformImpl createMovingPlatform(final Position pos, final Position a, final Position b) {
        return new PlatformImpl(pos, a, b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Button createButton(final Position pos) {
        return new Button(pos);
    }
}
