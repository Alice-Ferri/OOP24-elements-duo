package it.unibo.elementsduo.model.collisionevents.impl;

import it.unibo.elementsduo.model.collisionevents.api.Event;
import it.unibo.elementsduo.model.enemies.api.Enemy;

public class EnemyDiedEvent implements Event {

    private final Enemy enemy;

    public EnemyDiedEvent(final Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return this.enemy;
    }
}
