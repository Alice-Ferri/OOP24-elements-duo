package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.HashSet;
import java.util.Set;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.button;

public class ButtonActivationHandler implements CollisionHandler {

    private Set<button> pressedButtons = new HashSet<>();

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return (a instanceof Player && b instanceof button) || (b instanceof Player && a instanceof button);
    }

    @Override
    public void handle(CollisionInformations c) {
        button b;
        if (c.getObjectA() instanceof button)
            b = (button) c.getObjectA();
        else
            b = (button) c.getObjectB();
        if (!pressedButtons.contains(b)) {
            b.activate();
            pressedButtons.add(b);
        }
    }

    public void atEndCollision(button b) {
        b.deactivate();
        pressedButtons.remove(b);
    }

}
