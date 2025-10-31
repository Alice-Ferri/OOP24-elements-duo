package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.enemies.api.Enemy;

public record EnemyDiedEvent(Enemy enemy) implements Event {

}
