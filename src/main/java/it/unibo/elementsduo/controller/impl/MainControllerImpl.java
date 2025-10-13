package it.unibo.elementsduo.controller.impl;

import javax.swing.SwingUtilities;

import it.unibo.elementsduo.controller.gameLoop;
import it.unibo.elementsduo.controller.api.MainController;
import it.unibo.elementsduo.model.enemies.impl.EnemyFactoryImpl;
import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.map.impl.MapLoader;
import it.unibo.elementsduo.model.obstacles.impl.obstacleFactory;
import it.unibo.elementsduo.view.GameFrame;

public class MainControllerImpl implements MainController {

    private final Level level;
    private final GameFrame view;
    private final gameLoop gameLoop;

    public MainControllerImpl(){

        final MapLoader mapLoader = new MapLoader(new obstacleFactory(), new EnemyFactoryImpl());
        try {
            this.level = mapLoader.loadLevel(1);
        } catch (final Exception e) {
            throw new IllegalStateException("Impossibile caricare il livello.", e);
        }
        
        this.view = new GameFrame(level);
        this.gameLoop = new gameLoop();
    }

    @Override
    public void update(double deltaTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    
}