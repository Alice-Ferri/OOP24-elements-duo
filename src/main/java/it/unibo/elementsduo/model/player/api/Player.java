package it.unibo.elementsduo.model.player.api;

import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.model.player.api.ports.PlayerWorldPort;

public interface Player {
    PlayerType getType();

    /** Posizione in tile*/
    Position getPosition();

    /** True se è a contatto col terreno (per consentire il salto) */
    boolean isOnGround();

    /** Input impostato dal Controller*/
    void setInput(PlayerInput input);

    /** Avanza di un step */
    PlayerStepResult step(double dt, PlayerWorldPort port);
}
