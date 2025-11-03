package it.unibo.elementsduo.model.powerups.api;

import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.player.api.Player;

public interface PowerUpEffect {

    void onActivated(Player player, EventManager eventManager, double duration);

    void onRefreshed(Player player, EventManager eventManager, double duration);

    boolean onUpdate(Player player, EventManager eventManager, double deltaTime);

    void onExpired(Player player, EventManager eventManager);

}
