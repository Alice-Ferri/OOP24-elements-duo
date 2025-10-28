package it.unibo.elementsduo.controller.gamecontroller.impl;

import javax.swing.JPanel;

import it.unibo.elementsduo.controller.GameLoop;
import it.unibo.elementsduo.controller.api.EnemiesMoveManager;
import it.unibo.elementsduo.controller.gamecontroller.api.GameController;
import it.unibo.elementsduo.controller.impl.EnemiesMoveManagerImpl;
import it.unibo.elementsduo.controller.impl.InputController;
import it.unibo.elementsduo.controller.maincontroller.api.GameNavigation;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionManager;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.events.impl.EventManager;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.view.LevelPanel;

public class GameControllerImpl implements GameController {

    private final Level level;
    private final LevelPanel view;
    private final GameLoop gameLoop;
    private final EnemiesMoveManager moveManager;
    private final GameNavigation controller; // lo utilizzerò quando sarà gestito lo stop al gameloop
    private final CollisionManager collisionManager;
    private final InputController inputController = new InputController();

    public GameControllerImpl(final Level level, final GameNavigation controller) {

        this.level = level;
        this.controller = controller;
        this.view = new LevelPanel(this.level);
        this.gameLoop = new GameLoop(this);
        this.moveManager = new EnemiesMoveManagerImpl(level.getAllObstacles());
        this.collisionManager = new CollisionManager(new EventManager());
        this.inputController.install();
    }

    @Override
    public void update(double deltaTime) {
        this.level.getAllEnemies().forEach(obj -> {
            obj.update(deltaTime);
            if (obj instanceof ShooterEnemyImpl) {
                ((ShooterEnemyImpl) obj).attack().ifPresent(level::addProjectile);
            }
        });
        this.level.getAllProjectiles().forEach(p -> p.update(deltaTime));
        this.level.getAllPlayers().forEach(p -> p.update(deltaTime, inputController));
        this.collisionManager.manageCollisions(this.level.getAllCollidables());
    }

    @Override
    public void render() {
        this.view.repaint();
    }

    public void start() {
        this.view.setVisible(true);
        level.setEnemiesMoveManager(moveManager);
        this.gameLoop.start();
    }

    @Override
    public void activate() {
        this.start();
    }

    @Override
    public void deactivate() {
        this.gameLoop.stop();
    }

    @Override
    public JPanel getPanel() {
        return this.view;
    }

}