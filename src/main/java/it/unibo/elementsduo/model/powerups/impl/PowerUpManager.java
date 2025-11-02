package it.unibo.elementsduo.model.powerups.impl;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PowerUpCollectedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.PowerUpExpiredEvent;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.powerups.api.PowerUp;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;

public class PowerUpManager {
    private final EventManager eventManager;
    private final Map<Player, Map<PowerUpType, ActivePowerUp>> activeEffects = new HashMap<>();

    public PowerUpManager(EventManager eventManager) {
        this.eventManager = Objects.requireNonNull(eventManager);
    }

    public void activate(final PowerUp powerUp, final Player player) {
        final Map<PowerUpType, ActivePowerUp> playerEffects = this.activeEffects
                .computeIfAbsent(player, p -> new EnumMap<>(PowerUpType.class));

        final PowerUpType type = powerUp.getType();
        final double duration = powerUp.getDuration();

        final ActivePowerUp existingEffect = playerEffects.get(type);

        if (existingEffect == null) {
            final ActivePowerUp newEffect = new ActivePowerUp(player, type, duration);
            playerEffects.put(type, newEffect);
        } else {
            existingEffect.refresh(duration);
        }

        this.eventManager.notify(new PowerUpCollectedEvent(player, type));
    }

    public boolean hasEffect(final Player player, final PowerUpType type) {
        final Map<PowerUpType, ActivePowerUp> effects = this.activeEffects.get(player);
        return effects != null && effects.containsKey(type);
    }

    public void update(final double deltaTime) {
        final Iterator<Map.Entry<Player, Map<PowerUpType, ActivePowerUp>>> playersIterator = this.activeEffects
                .entrySet().iterator();

        while (playersIterator.hasNext()) {
            final Map.Entry<Player, Map<PowerUpType, ActivePowerUp>> entry = playersIterator.next();
            final Map<PowerUpType, ActivePowerUp> effects = entry.getValue();
            final Iterator<Map.Entry<PowerUpType, ActivePowerUp>> effectsIterator = effects.entrySet().iterator();

            while (effectsIterator.hasNext()) {
                final ActivePowerUp active = effectsIterator.next().getValue();
                active.sub(deltaTime);
                if (active.hasExpired()) {
                    effectsIterator.remove();
                    this.eventManager.notify(new PowerUpExpiredEvent(active.player, active.type));
                }
            }

            if (effects.isEmpty()) {
                playersIterator.remove();
            }
        }
    }

    private static final class ActivePowerUp {
        private final Player player;
        private final PowerUpType type;
        private double remaining;

        private ActivePowerUp(final Player player, final PowerUpType type, final double duration) {
            this.player = player;
            this.type = type;
            this.remaining = duration;
        }

        private void refresh(final double duration) {
            this.remaining = duration;
        }

        private void sub(final double deltaTime) {
            this.remaining -= deltaTime;
        }

        private boolean hasExpired() {
            return this.remaining <= 0.0;
        }

    }
}
