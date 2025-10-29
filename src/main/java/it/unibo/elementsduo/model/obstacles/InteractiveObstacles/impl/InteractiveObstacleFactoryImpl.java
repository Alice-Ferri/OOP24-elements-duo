package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.InteractiveObstacleFactory;
import it.unibo.elementsduo.resources.Position;

public class InteractiveObstacleFactoryImpl implements InteractiveObstacleFactory {

    @Override
    public Lever createLever(Position pos) {
        return new Lever(pos);
    }

    @Override
    public PushBox createPushBox(Position pos) {
        return new PushBox(pos);
    }

    @Override
    public PlatformImpl createMovingPlatform(Position pos, Position a, Position b) {
        return new PlatformImpl(pos, a, b);
    }

    @Override
    public button createButton(Position pos) {
        return new button(pos);
    }

}
