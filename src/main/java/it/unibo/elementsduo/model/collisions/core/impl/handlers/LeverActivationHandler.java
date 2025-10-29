package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.elementsduo.model.collisions.commands.impl.LeverActivationCommand;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionRespinse;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.player.api.Player;

public class LeverActivationHandler implements CollisionHandler {

    private List<Lever> leversThisFrame = new ArrayList<>();
    private List<Lever> leversLastFrame = new ArrayList<>();

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return (a instanceof Player && b instanceof Lever) || (b instanceof Player && a instanceof Lever);
    }

    @Override
    public void handle(CollisionInformations c, CollisionRespinse collisionResponse) {
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

        leversThisFrame.add(trigger);
        /* if it isn't in the levers before so it is a new lever colliding */
        if (!leversLastFrame.contains(trigger)) {
            collisionResponse.addCommand(new LeverActivationCommand(trigger));
        }
    }

    public void onUpdateStart() {
        leversThisFrame.clear();
    }

    public void onUpdateEnd() {
        /* the levers of this cicle become the old levers */
        leversLastFrame.clear();
        leversLastFrame.addAll(leversThisFrame);
    }

}
