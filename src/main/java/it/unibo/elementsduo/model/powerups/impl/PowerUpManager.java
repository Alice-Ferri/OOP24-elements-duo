package it.unibo.elementsduo.model.powerups.impl;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.collisions.events.api.EventListener;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PowerUpCollectedEvent;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.powerups.api.PowerUpEffect;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;

public class PowerUpManager implements EventListener {

    private final Map<PlayerType, Map<PowerUpType, ActivePowerUp>> activeEffects = new EnumMap<>(PlayerType.class);
    private final Map<PlayerType, Player> players = new EnumMap<>(PlayerType.class);

    public PowerUpManager(final EventManager eventManager) {
        Objects.requireNonNull(eventManager);
        eventManager.subscribe(PowerUpCollectedEvent.class, this);
    }

    public boolean hasEffect(final Player player, final PowerUpType type) {
        final Map<PowerUpType, ActivePowerUp> effects = this.activeEffects.get(player);
        return effects != null && effects.containsKey(type);
    }

    public void registerPlayer(final Player player) {
        this.players.put(player.getPlayerType(), player);
    }

    @Override
    public void onEvent(final Event event) {
        if (event instanceof PowerUpCollectedEvent collected) {
            Player player = this.players.get(collected.playerType());
            if (player == null) {
                return;
            }
            final Map<PowerUpType, ActivePowerUp> playerEffects = this.activeEffects
                    .computeIfAbsent(collected.playerType(), p -> new EnumMap<>(PowerUpType.class));

            playerEffects.compute(collected.type(), (t, existing) -> {
                if (existing == null) {
                    return new ActivePowerUp(player, collected.type(),
                            collected.effect(), collected.duration());
                }
                existing.refresh(collected.duration());
                return existing;
            });
        }
    }

    public void update(final double deltaTime) {
        final Iterator<Map.Entry<PlayerType, Map<PowerUpType, ActivePowerUp>>> playersIterator = this.activeEffects
                .entrySet().iterator();

        while (playersIterator.hasNext()) {
            final Map.Entry<PlayerType, Map<PowerUpType, ActivePowerUp>> entry = playersIterator.next();
            final Map<PowerUpType, ActivePowerUp> effects = entry.getValue();
            final Iterator<Map.Entry<PowerUpType, ActivePowerUp>> effectsIterator = effects.entrySet().iterator();

            while (effectsIterator.hasNext()) {
                final ActivePowerUp active = effectsIterator.next().getValue();
                if (!active.update(deltaTime)) {
                    effectsIterator.remove();
                }
            }

            if (effects.isEmpty()) {
                playersIterator.remove();
            }
        }
    }

    private static final class ActivePowerUp {
        private final Player player;
        private final PowerUpEffect effect;

        private ActivePowerUp(final Player player, final PowerUpType type, final PowerUpEffect effect,
                final double duration) {
            this.player = player;
            this.effect = effect;
            this.effect.onActivated(this.player, duration);
        }

        private void refresh(final double duration) {
            this.effect.onRefreshed(this.player, duration);
        }

        private boolean update(final double deltaTime) {
            final boolean stillActive = this.effect.onUpdate(this.player, deltaTime);
            if (!stillActive) {
                this.effect.onExpired(this.player);
            }
            return stillActive;
        }

    }
}
