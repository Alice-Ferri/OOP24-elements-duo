package it.unibo.elementsduo.model.enemies.api;

import it.unibo.elementsduo.resources.Position;

public interface EnemyFactory {
    Enemy createEnemy(char c, Position pos);
}
