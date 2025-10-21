package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.player.api.Player;

public class LeverActivationHandler implements CollisionHandler {

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return (a instanceof Player && b instanceof Lever) || (b instanceof Player && a instanceof Lever);
    }

    @Override
    public void handle(CollisionInformations c) {
        Player player = null;
        Collidable trigger = null;
        Collidable a = c.getObjectA();
        Collidable b = c.getObjectB();

        if (a instanceof Player) {
            player = (Player) a;
            trigger = b;
        } else if (b instanceof Player) {
            player = (Player) b;
            trigger = a;
        }

        if (player == null || trigger == null)
            return;

        if (trigger instanceof Lever lever)
            lever.toggle();
    }

}
