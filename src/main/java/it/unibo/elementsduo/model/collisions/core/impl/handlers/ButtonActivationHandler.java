package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Pressable;
import it.unibo.elementsduo.model.player.api.Player;

public final class ButtonActivationHandler extends AbstractCollisionHandler<Player, Pressable> {

    private final List<Pressable> pressablesThisFrame = new ArrayList<>();
    private final List<Pressable> pressablesLastFrame = new ArrayList<>();

    public ButtonActivationHandler() {
        super(Player.class, Pressable.class);
    }

    @Override
    public void handleCollision(final Player player, final Pressable b, final CollisionInformations c,
            final CollisionResponse.Builder builder) {

        pressablesThisFrame.add(b);

        if (!pressablesLastFrame.contains(b)) {
            builder.addLogicCommand(b::press);
        }
    }

    @Override
    public void onUpdateStart() {
        pressablesThisFrame.clear();
    }

    @Override
    public void onUpdateEnd() {
        pressablesLastFrame.stream()
                .filter(b -> !pressablesThisFrame.contains(b))
                .forEach(Pressable::release);

        pressablesLastFrame.clear();
        pressablesLastFrame.addAll(pressablesThisFrame);
    }
}