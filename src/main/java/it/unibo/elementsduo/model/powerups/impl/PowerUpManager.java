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
import it.unibo.elementsduo.model.collisions.events.impl.PowerUpExpiredEvent;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.powerups.api.PowerUpEffect;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;

public class PowerUpManager implements EventListener {

    private final EventManager eventManager;
    private final Map<Player, Map<PowerUpType, ActivePowerUp>> activeEffects = new HashMap<>();
    private static PowerUpManager instance;

    public PowerUpManager(final EventManager eventManager) {
        this.eventManager = Objects.requireNonNull(eventManager);
        this.eventManager.subscribe(PowerUpCollectedEvent.class, this);
        instance = this;
    }

    public boolean hasEffect(final Player player, final PowerUpType type) {
        final Map<PowerUpType, ActivePowerUp> effects = this.activeEffects.get(player);
        return effects != null && effects.containsKey(type);
    }

    @Override
    public void onEvent(final Event event) {
        if (event instanceof PowerUpCollectedEvent collected) {
            final Map<PowerUpType, ActivePowerUp> playerEffects = this.activeEffects
                    .computeIfAbsent(collected.player(), p -> new EnumMap<>(PowerUpType.class));

            playerEffects.compute(collected.type(), (t, existing) -> {
                if (existing == null) {
                    return new ActivePowerUp(collected.player(), collected.type(),
                            collected.strategy(), collected.duration(), this.eventManager);
                }
                existing.refresh(collected.duration());
                return existing;
            });
        }
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
                if (!active.update(deltaTime)) {
                    effectsIterator.remove();
                    this.eventManager.notify(new PowerUpExpiredEvent(active.player(), active.type()));
                }
            }

            if (effects.isEmpty()) {
                playersIterator.remove();
            }
        }
    }

    public static PowerUpManager getInstance() {
        return instance;
    }

    private static final class ActivePowerUp {
        private final Player player;
        private final PowerUpType type;
        private final PowerUpEffect strategy;
        private final EventManager eventManager;

        private ActivePowerUp(final Player player, final PowerUpType type, final PowerUpEffect strategy,
                final double duration, final EventManager eventManager) {
            this.player = player;
            this.type = type;
            this.strategy = strategy;
            this.eventManager = eventManager;
            this.strategy.onActivated(this.player, this.eventManager, duration);
        }

        private void refresh(final double duration) {
            this.strategy.onRefreshed(this.player, this.eventManager, duration);
        }

        private boolean update(final double deltaTime) {
            final boolean stillActive = this.strategy.onUpdate(this.player, this.eventManager, deltaTime);
            if (!stillActive) {
                this.strategy.onExpired(this.player, this.eventManager);
            }
            return stillActive;
        }

        private Player player() {
            return this.player;
        }

        private PowerUpType type() {
            return this.type;
        }

    }
}
