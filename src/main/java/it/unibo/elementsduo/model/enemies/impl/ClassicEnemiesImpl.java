package it.unibo.elementsduo.model.enemies.impl;

import it.unibo.elementsduo.resources.Position;

/**
 * Implementation of the classic (patrol) enemy.
 * Extends AbstractEnemy, inheriting all state, physics,
 * and movement logic.
 * Its only specialization is having no additional behaviors.
 */
public final class ClassicEnemiesImpl extends AbstractEnemy {

    /**
     * Constructor for the classic enemy.
     *
     * @param pos the starting position.
     */
    public ClassicEnemiesImpl(final Position pos) {
        // Calls the abstract class constructor
        super(pos);
    }

    /**
     * {@inheritDoc}
     * Implementation of the Template Pattern's "hook" method.
     * The classic enemy has no specific behaviors beyond the
     * base movement (already handled by AbstractEnemy.update()).
     */
    @Override
    protected void updateAttack(final double deltaTime) {
        // Intentionally left empty.
    }

    /*
     * Note: It is not necessary to implement attack(), as the
     * default implementation in AbstractEnemy (which returns
     * Optional.empty()) is exactly the desired behavior.
     */
}

