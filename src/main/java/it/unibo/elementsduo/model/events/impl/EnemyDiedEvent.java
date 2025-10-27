package it.unibo.elementsduo.model.events.impl;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.events.api.Event;

public class EnemyDiedEvent implements Event {

    Enemy enemy;

    public EnemyDiedEvent(final Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return this.enemy;
    }
}
