package it.unibo.elementsduo.model.powerups.api;

import it.unibo.elementsduo.model.player.api.Player;

public interface PowerUpEffect {

    void onActivated(Player player, double duration);

    void onRefreshed(Player player, double duration);

    boolean onUpdate(Player player, double deltaTime);

    void onExpired(Player player);

}
