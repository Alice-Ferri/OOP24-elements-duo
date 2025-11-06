package it.unibo.elementsduo.model.interactions.core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import it.unibo.elementsduo.model.interactions.core.api.InteractionHandler;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionInformations;

/**
 * Manages the registration and execution of all {@link InteractionHandler}
 * instances.
 * 
 * <p>
 * The {@code CollisionHandlersRegister} stores all registered handlers,
 * determines
 * which handlers can process specific collisions, and notifies them at the
 * start
 * and end of each update cycle.
 */
public final class InteractionsHandlersRegister {

    private final List<InteractionHandler> register = new ArrayList<>();
    private final Map<Class<? extends InteractionHandler>, InteractionHandler> handlersMap = new HashMap<>();

    /**
     * Registers a new {@link InteractionHandler} if it has not already been added.
     *
     * @param ch the collision handler to register
     * @throws NullPointerException if the provided handler is {@code null}
     */
    public void registerHandler(final InteractionHandler ch) {
        Objects.requireNonNull(ch);
        handlersMap.computeIfAbsent(ch.getClass(), clazz -> {
            register.add(ch);
            return ch;
        });
    }

    /**
     * Retrieves a registered handler of the specified type.
     *
     * @param <T>  the type of the handler
     * @param type the class object of the handler type
     * @return an {@link Optional} containing the handler if found, otherwise empty
     */
    public <T extends InteractionHandler> Optional<T> getHandler(final Class<T> type) {
        return Optional.ofNullable(type.cast(handlersMap.get(type)));
    }

    /**
     * Passes collision information to all compatible handlers.
     * 
     * <p>
     * Each handler that supports the types of the involved objects will process
     * the collision accordingly.
     *
     * @param info    the collision information
     * @param builder the builder used to collect collision responses
     */
    public void handle(final CollisionInformations info, final InteractionResponse.Builder builder) {
        register.stream()
                .filter(handler -> handler.canHandle(info.getObjectA(), info.getObjectB()))
                .forEach(handler -> handler.handle(info, builder));
    }

    /**
     * Notifies all registered handlers that a new update cycle is starting.
     * 
     * <p>
     * This is typically used to reset per-frame state within handlers.
     */
    public void notifyUpdateStart() {
        register.forEach(InteractionHandler::onUpdateStart);
    }

    /**
     * Notifies all registered handlers that the update cycle has ended.
     * 
     * <p>
     * This allows handlers to perform cleanup or finalize collision logic.
     */
    public void notifyUpdateEnd() {
        register.forEach(InteractionHandler::onUpdateEnd);
    }
}
