package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.resources.Position;

public interface InteractiveObstacleFactory {
    Lever createLever(Position pos, double halfWidth, double halfHeight);

    PushBox createPushBox(Position pos, double halfWidth, double halfHeight, double mass);

    PlatformImpl createMovingPlatform(Position pos, Position a, Position b, double halfWidth, double halfHeight,
            double speed);
}
