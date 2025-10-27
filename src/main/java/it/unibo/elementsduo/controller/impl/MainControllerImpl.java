package it.unibo.elementsduo.controller.impl;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import it.unibo.elementsduo.controller.GameLoop;
import it.unibo.elementsduo.controller.api.MainController;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.enemies.impl.EnemyFactoryImpl;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.map.level.MapLoader;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.map.mapvalidator.api.InvalidMapException;
import it.unibo.elementsduo.model.map.mapvalidator.api.MapValidator;
import it.unibo.elementsduo.model.map.mapvalidator.impl.MapValidatorImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.obstacleFactory;
import it.unibo.elementsduo.view.GameFrame;

public class MainControllerImpl implements MainController {

    private final Level level;
    private final GameFrame view;
    private final GameLoop gameLoop;
    private final Set<Projectiles> projectiles = new HashSet<>();
    private final MapValidator mapValidator = new MapValidatorImpl();

    public MainControllerImpl(){

        final MapLoader mapLoader = new MapLoader(new obstacleFactory(), new EnemyFactoryImpl(),new InteractiveObstacleFactoryImpl());
        try {
            this.level = mapLoader.loadLevel(1);
            this.mapValidator.validate(level);

        } catch (InvalidMapException | IllegalArgumentException e) {
            throw new IllegalStateException("Errore nel caricamento o validazione del livello: " + e.getMessage());
        
        }
        
        this.view = new GameFrame(level);
        this.gameLoop = new GameLoop(this);
    }

    @Override
    public void update(double deltaTime) {
        
        this.level.getAllEnemies().forEach(obj -> {
            obj.update(level.getAllObstacles(), deltaTime);
        });
        
    }

    @Override
    public void render() {
        this.view.repaint();
    }

    public void start(){
        this.view.setVisible(true);
        this.gameLoop.start();
    }

    
}