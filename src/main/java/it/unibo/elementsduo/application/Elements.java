package it.unibo.elementsduo.application;

import javax.swing.SwingUtilities;

import it.unibo.elementsduo.model.enemies.impl.EnemyFactoryImpl;
import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.map.impl.MapLoader;
import it.unibo.elementsduo.model.obstacles.impl.obstacleFactory;
import it.unibo.elementsduo.view.GameFrame;

public class Elements {
    public static void main(String[] args) {
        MapLoader loader = new MapLoader(new obstacleFactory(),new EnemyFactoryImpl());
        Level level = loader.loadLevel(1);
        SwingUtilities.invokeLater(() -> new GameFrame(level));
    }
}

