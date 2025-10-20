package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.InteractiveObstacleFactory;
import it.unibo.elementsduo.resources.Position;

public class InteractiveObstacleFactoryImpl implements InteractiveObstacleFactory {

    @Override
    public Lever createLever(Position pos, double halfWidth, double halfHeight) {
        return new Lever(pos, halfWidth, halfHeight);
    }

    @Override
    public PushBox createPushBox(Position pos, double halfWidth, double halfHeight, double mass) {
        return new PushBox(pos, halfWidth, halfHeight, mass);
    }

    @Override
    public PlatformImpl createMovingPlatform(Position pos, Position a, Position b, double halfWidth, double halfHeight,
            double speed) {
        return new PlatformImpl(pos, a, b, halfWidth, halfHeight, speed);
    }

}
