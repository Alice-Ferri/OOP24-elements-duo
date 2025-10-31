package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.button;

public class ButtonActivationHandler extends AbstractCollisionHandler<Player, button> {

    private List<button> buttonsThisFrame = new ArrayList<>();
    private List<button> buttonsLastFrame = new ArrayList<>();

    public ButtonActivationHandler() {
        super(Player.class, button.class);
    }

    @Override
    public void handleCollision(Player player, button b, CollisionInformations c, CollisionResponse.Builder builder) {
        buttonsThisFrame.add(b);

        if (!buttonsLastFrame.contains(b)) {
            builder.addLogicCommand(() -> b.activate());
        }
    }

    public void onUpdateStart() {
        buttonsThisFrame.clear();
    }

    public void onUpdateEnd() {
        List<button> releasedButtons = new ArrayList<>(buttonsLastFrame);
        releasedButtons.removeAll(buttonsThisFrame);

        for (button b : releasedButtons) {
            b.deactivate();
        }

        buttonsLastFrame.clear();
        buttonsLastFrame.addAll(buttonsThisFrame);
    }

}
