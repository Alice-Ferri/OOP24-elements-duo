package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.collisions.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.player.api.Player;

public class PlayerEnemyCommand implements CollisionCommand {

    private final Player player;
    private final Enemy enemy;
    private final EventManager eventManager;
    private final boolean isOn;

    public PlayerEnemyCommand(Player player, Enemy enemy, EventManager eventManager, boolean isOn) {
        this.player = player;
        this.enemy = enemy;
        this.eventManager = eventManager;
        this.isOn = isOn;
    }

    @Override
    public void execute() {
        if (isOn) {
            enemy.die();
            this.eventManager.notify(new EnemyDiedEvent(enemy));
        } else {
            this.eventManager.notify(new PlayerDiedEvent(player));
        }
    }

}
