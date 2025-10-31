package it.unibo.elementsduo.application;

import it.unibo.elementsduo.controller.maincontroller.impl.MainControllerImpl;

public final class Elements {

    private Elements() { }

    public static void main(final String[] args) {
        new MainControllerImpl().startApp();
    }
}
