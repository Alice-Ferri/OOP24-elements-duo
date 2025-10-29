package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.elementsduo.model.collisions.commands.impl.ButtonActivationCommand;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.button;

public class ButtonActivationHandler implements CollisionHandler {

    private List<button> buttonsThisFrame = new ArrayList<>();
    private List<button> buttonsLastFrame = new ArrayList<>();

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return (a instanceof Player && b instanceof button) || (b instanceof Player && a instanceof button);
    }

    @Override
    public void handle(CollisionInformations c, CollisionResponse collisionResponse) {
        button b;
        if (c.getObjectA() instanceof button)
            b = (button) c.getObjectA();
        else
            b = (button) c.getObjectB();

        buttonsThisFrame.add(b);

        if (!buttonsLastFrame.contains(b)) {
            collisionResponse.addCommand(new ButtonActivationCommand(b));
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
