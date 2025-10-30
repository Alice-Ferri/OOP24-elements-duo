package it.unibo.elementsduo.model.gameentity.api;

@FunctionalInterface
public interface Updatable {
    void update(double deltatime);
}
