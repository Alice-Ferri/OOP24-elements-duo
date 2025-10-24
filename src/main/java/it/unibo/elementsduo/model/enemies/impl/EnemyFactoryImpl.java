package it.unibo.elementsduo.model.enemies.impl;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.EnemyFactory;
import it.unibo.elementsduo.resources.Position;

/**
 * Implementation of the EnemyFactory interface for creating different enemy types.
 */
public final class EnemyFactoryImpl implements EnemyFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createEnemy(final char c, final Position pos) { 
        return switch (c) {
            case 'C' -> new ClassicEnemiesImpl(pos); 
            case 'S' -> new ShooterEnemyImpl(pos); 
            default -> throw new IllegalArgumentException("Unknown enemy type: " + c);

        };
    }
}
