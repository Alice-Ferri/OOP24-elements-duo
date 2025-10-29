package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.button;
import it.unibo.elementsduo.resources.Position;

public interface InteractiveObstacleFactory {
    Lever createLever(Position pos);

    PushBox createPushBox(Position pos);

    PlatformImpl createMovingPlatform(Position pos, Position a, Position b);

    button createButton(Position pos);
}
