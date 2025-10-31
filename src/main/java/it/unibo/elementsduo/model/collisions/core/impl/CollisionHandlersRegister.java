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
    private final List<CollisionHandler> register = new ArrayList<>();
    private final Map<Class<? extends CollisionHandler>, CollisionHandler> handlersMap = new HashMap<>();

    public void registerHandler(CollisionHandler ch) {
        Objects.requireNonNull(ch);
        handlersMap.computeIfAbsent(ch.getClass(), clazz -> {
            register.add(ch);
            return ch;
        });
    }

    public <T extends CollisionHandler> Optional<T> getHandler(Class<T> type) {
        return Optional.ofNullable((T) handlersMap.get(type));
    }

    public void handle(CollisionInformations info, CollisionResponse.Builder builder) {
        register.stream()
                .filter(handler -> handler.canHandle(info.getObjectA(), info.getObjectB()))
                .forEach(handler -> handler.handle(info, builder));
    }

    /* notify handlers the beginning and the end */
    public void notifyUpdateStart() {
        register.forEach(CollisionHandler::onUpdateStart);
    }

    public void notifyUpdateEnd() {
        register.forEach(CollisionHandler::onUpdateEnd);
    }
}
