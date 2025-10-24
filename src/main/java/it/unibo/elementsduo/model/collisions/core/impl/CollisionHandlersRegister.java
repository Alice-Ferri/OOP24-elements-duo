package it.unibo.elementsduo.model.collisions.core.impl;

import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;

import java.util.ArrayList;
import java.util.List;

public class CollisionHandlersRegister {
    private List<CollisionHandler> register = new ArrayList<>();

    public void registerHandler(CollisionHandler ch) {
        register.add(ch);
    }

    public void handle(CollisionInformations info) {
        for (var handler : register) {
            if (handler.canHandle(info.getObjectA(), info.getObjectB())) {
                handler.handle(info);
            }
        }
    }
}
