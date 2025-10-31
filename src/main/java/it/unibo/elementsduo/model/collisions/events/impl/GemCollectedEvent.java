package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Gem;
import it.unibo.elementsduo.model.player.api.Player;

public record GemCollectedEvent(Player player, Gem gem) implements Event {
}
