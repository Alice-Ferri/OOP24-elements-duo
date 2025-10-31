package it.unibo.elementsduo.model.collisions.core.impl;

import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CollisionHandlersRegister {
    private List<CollisionHandler> register = new ArrayList<>();
    private Map<Class<? extends CollisionHandler>, CollisionHandler> handlersMap = new HashMap<>();

    public void registerHandler(CollisionHandler ch) {
        Objects.requireNonNull(ch);
        if (!handlersMap.containsKey(ch.getClass())) {
            handlersMap.put(ch.getClass(), ch);
            register.add(ch);
        }
    }

    public <T extends CollisionHandler> Optional<T> getHandler(Class<T> type) {
        return Optional.ofNullable((T) handlersMap.get(type));
    }

    public void handle(CollisionInformations info, CollisionResponse.Builder builder) {
        for (var handler : register) {
            if (handler.canHandle(info.getObjectA(), info.getObjectB())) {
                handler.handle(info, builder);
            }
        }
    }

    /* notify handlers the beginning and the end */
    public void notifyUpdateStart() {
        for (var handler : register) {
            handler.onUpdateStart();
        }
    }

    public void notifyUpdateEnd() {
        for (var handler : register) {
            handler.onUpdateEnd();
        }
    }
}
