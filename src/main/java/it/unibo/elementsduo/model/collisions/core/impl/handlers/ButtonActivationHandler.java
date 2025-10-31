package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.commands.impl.ButtonActivationCommand;
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
    public void handleCollision(Player player, button b, CollisionInformations c, CollisionResponse collisionResponse) {
        buttonsThisFrame.add(b);

        if (!buttonsLastFrame.contains(b)) {
            collisionResponse.addLogicCommand(new ButtonActivationCommand(b));
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
