package it.unibo.elementsduo.application;

import it.unibo.elementsduo.controller.impl.MainControllerImpl;

public class Elements {
    public static void main(String[] args) {
        /*
        MapLoader loader = new MapLoader(new obstacleFactory(),new EnemyFactoryImpl());
        Level level = loader.loadLevel(1);
        SwingUtilities.invokeLater(() -> new GameFrame(level));*/

        new MainControllerImpl().start();
    }
}

