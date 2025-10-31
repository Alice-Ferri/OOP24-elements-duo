package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.player.api.Player;

public class LeverActivationHandler extends AbstractCollisionHandler<Player, Lever> {

    private List<Lever> leversThisFrame = new ArrayList<>();
    private List<Lever> leversLastFrame = new ArrayList<>();

    public LeverActivationHandler() {
        super(Player.class, Lever.class);
    }

    @Override
    public void handleCollision(Player player, Lever trigger, CollisionInformations c,
            CollisionResponse.Builder builder) {

        leversThisFrame.add(trigger);
        /* if it isn't in the levers before so it is a new lever colliding */
        if (!leversLastFrame.contains(trigger)) {
            builder.addLogicCommand(() -> trigger.toggle());
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
