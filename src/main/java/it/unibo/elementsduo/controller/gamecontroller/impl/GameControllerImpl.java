package it.unibo.elementsduo.controller.gamecontroller.impl;

import javax.swing.JPanel;

import it.unibo.elementsduo.controller.GameLoop;
import it.unibo.elementsduo.controller.gamecontroller.api.GameController;
import it.unibo.elementsduo.model.enemies.impl.EnemyFactoryImpl;
import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.map.impl.MapLoader;
import it.unibo.elementsduo.model.obstacles.impl.obstacleFactory;
import it.unibo.elementsduo.view.GameFrame;

public class GameControllerImpl implements GameController {

    private final Level level;
    private final GameFrame view;
    private final GameLoop gameLoop;

    public GameControllerImpl(){

        final MapLoader mapLoader = new MapLoader(new obstacleFactory(), new EnemyFactoryImpl());
        try {
            this.level = mapLoader.loadLevel(1);
        } catch (final Exception e) {
            throw new IllegalStateException("Impossibile caricare il livello.", e);
        }
        
        this.view = new GameFrame();
        this.gameLoop = new GameLoop(this);
    }

    @Override
    public void update(double deltaTime) {
        this.level.getAllEnemies().forEach(obj -> obj.update());
    }

    @Override
    public void render() {
        this.view.repaint();
    }

    public void start(){
        this.view.setVisible(true);
        this.gameLoop.start();
    }

    @Override
    public void activate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'activate'");
    }

    @Override
    public void deactivate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deactivate'");
    }

    @Override
    public JPanel getPanel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPanel'");
    }

    
}