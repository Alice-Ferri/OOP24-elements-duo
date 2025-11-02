package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Toggler;
import it.unibo.elementsduo.model.player.api.Player;

public final class LeverActivationHandler extends AbstractCollisionHandler<Player, Toggler> {

    private final List<Toggler> togglersThisFrame = new ArrayList<>();
    private final List<Toggler> togglersLastFrame = new ArrayList<>();

    public LeverActivationHandler() {
        super(Player.class, Toggler.class);
    }

    @Override
    public void handleCollision(final Player player, final Toggler trigger, final CollisionInformations c,
            final CollisionResponse.Builder builder) {

        togglersThisFrame.add(trigger);

        if (!togglersLastFrame.contains(trigger)) {
            builder.addLogicCommand(trigger::toggle);
        }
    }

    @Override
    public void onUpdateStart() {
        togglersThisFrame.clear();
    }

    @Override
    public void onUpdateEnd() {
        togglersLastFrame.clear();
        togglersLastFrame.addAll(togglersThisFrame);
    }
}