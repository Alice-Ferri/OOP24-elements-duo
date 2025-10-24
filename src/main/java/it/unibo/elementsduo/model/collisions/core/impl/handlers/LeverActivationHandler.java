package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.HashSet;
import java.util.Set;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.player.api.Player;

public class LeverActivationHandler implements CollisionHandler {

    private Set<Lever> activeLevers = new HashSet<>();

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return (a instanceof Player && b instanceof Lever) || (b instanceof Player && a instanceof Lever);
    }

    @Override
    public void handle(CollisionInformations c) {
        Player player = null;
        Lever trigger = null;
        Collidable a = c.getObjectA();
        Collidable b = c.getObjectB();

        if (a instanceof Player) {
            player = (Player) a;
            trigger = (Lever) b;
        } else if (b instanceof Player) {
            player = (Player) b;
            trigger = (Lever) a;
        }

        if (player == null || trigger == null)
            return;

        if (!this.activeLevers.contains(trigger)) {
            trigger.toggle();
            activeLevers.add(trigger);
        }
    }

    public void atEndCollision(Lever l) {
        this.activeLevers.remove(l);
    }

}
