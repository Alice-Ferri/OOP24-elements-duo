package it.unibo.elementsduo.model.enemies.api;

import it.unibo.elementsduo.utils.Position;

public interface EnemyFactory {
    Enemy createEnemy(char c,Position pos);
}
