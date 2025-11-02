package it.unibo.elementsduo.model.powerups.impl;

import it.unibo.elementsduo.model.powerups.api.PowerUp;
import it.unibo.elementsduo.model.powerups.api.PowerUpFactory;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;
import it.unibo.elementsduo.resources.Position;

public class PowerUpFactoryImpl implements PowerUpFactory {

    private static final double DEFAULT_HAZARD_IMMUNITY_DURATION = 5.0;
    private static final double DEFAULT_KILL_ENEMY_DURATION = 5.0;

    @Override
    public PowerUp createPowerUp(PowerUpType type, Position pos) {
        final double duration = switch (type) {
            case HAZARD_IMMUNITY -> DEFAULT_HAZARD_IMMUNITY_DURATION;
            case ENEMY_KILL -> DEFAULT_KILL_ENEMY_DURATION;
        };
        return new PowerUpImpl(type, pos, duration);
    }

}
