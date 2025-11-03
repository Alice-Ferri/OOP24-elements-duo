package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PowerUpCollectedEvent;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.powerups.api.PowerUp;

public final class PlayerPowerUpHandler extends AbstractCollisionHandler<Player, PowerUp> {

    private final EventManager eventManager;

    public PlayerPowerUpHandler(final EventManager eventManager) {
        super(Player.class, PowerUp.class);
        this.eventManager = eventManager;
    }

    @Override
    public void handleCollision(final Player player, final PowerUp powerUp, final CollisionInformations info,
            final CollisionResponse.Builder builder) {
        if (!powerUp.isActive()) {
            return;
        }

        builder.addLogicCommand(() -> {
            if (powerUp.isActive()) {
                powerUp.consume();
                this.eventManager.notify(new PowerUpCollectedEvent(player, powerUp.getType(),
                        powerUp.getDuration(), powerUp.getEffectStrategy()));
            }
        });
    }
}
