package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Hazard;
import it.unibo.elementsduo.model.player.api.Player;

public class PlayerHazardHandler implements CollisionHandler {

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return (a instanceof Player && b instanceof Hazard) || (b instanceof Player && a instanceof Hazard);
    }

    @Override
    public void handle(CollisionInformations c) {
        Player player;
        Hazard hazard;
        if (c.getObjectA() instanceof Player && c.getObjectB() instanceof Hazard) {
            player = (Player) c.getObjectA();
            hazard = (Hazard) c.getObjectB();
        } else if (c.getObjectA() instanceof Hazard && c.getObjectB() instanceof Player) {
            player = (Player) c.getObjectB();
            hazard = (Hazard) c.getObjectA();
        }
    }

}
