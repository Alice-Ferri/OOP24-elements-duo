package it.unibo.elementsduo.controller.impl;

import java.util.HashSet;
import java.util.Set;

import it.unibo.elementsduo.controller.GameLoop;
import it.unibo.elementsduo.controller.api.MainController;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.enemies.impl.EnemyFactoryImpl;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.map.impl.MapLoader;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.obstacleFactory;
import it.unibo.elementsduo.view.GameFrame;

public class MainControllerImpl implements MainController {

    private final Level level;
    private final GameFrame view;
    private final GameLoop gameLoop;
    private final Set<Projectiles> projectiles = new HashSet<>();
    private final InputController input;

    public MainControllerImpl() {

        final MapLoader mapLoader = new MapLoader(new obstacleFactory(), new EnemyFactoryImpl(),new InteractiveObstacleFactoryImpl());
        try {
            this.level = mapLoader.loadLevel(1);
        } catch (final Exception e) {
            throw new IllegalStateException("Impossibile caricare il livello.", e);
        }

        this.view = new GameFrame(level);
        this.gameLoop = new GameLoop(this);

        this.input = new InputController(this.level);
        this.input.install();

        this.view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                input.uninstall();
            }
        });
    }

    @Override
    public void update(double deltaTime) {
        this.level.getAllEnemies().forEach(obj -> {
            obj.update(level.getAllObstacles(), deltaTime);
        });
        this.input.update(deltaTime);
    }

    @Override
    public void render() {
        this.view.repaint();
    }

    public void start() {
        this.view.setVisible(true);
        this.gameLoop.start();
    }

}