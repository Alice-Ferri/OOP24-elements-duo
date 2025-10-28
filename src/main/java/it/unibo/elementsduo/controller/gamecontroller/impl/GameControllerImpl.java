package it.unibo.elementsduo.controller.gamecontroller.impl;

import javax.swing.JPanel;

import it.unibo.elementsduo.controller.GameLoop;
import it.unibo.elementsduo.controller.api.EnemiesMoveManager;
import it.unibo.elementsduo.controller.gamecontroller.api.GameController;
import it.unibo.elementsduo.controller.impl.EnemiesMoveManagerImpl;
import it.unibo.elementsduo.controller.impl.InputController;
import it.unibo.elementsduo.controller.maincontroller.api.GameNavigation;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionManager;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.events.impl.EventManager;
import it.unibo.elementsduo.model.events.impl.ProjectileSolidEvent;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.view.LevelPanel;

public class GameControllerImpl implements GameController {

    private final Level level;
    private final LevelPanel view;
    private final GameLoop gameLoop;
    private final EnemiesMoveManager moveManager;
    private final GameNavigation controller; // lo utilizzerò quando sarà gestito lo stop al gameloop
    private final CollisionManager collisionManager;
    private final InputController inputController = new InputController();
    private final EventManager eventManager = new EventManager();

    public GameControllerImpl(final Level level, final GameNavigation controller) {

        this.level = level;
        this.controller = controller;
        this.view = new LevelPanel(this.level);
        this.gameLoop = new GameLoop(this);
        this.moveManager = new EnemiesMoveManagerImpl(level.getAllObstacles());
        this.collisionManager = new CollisionManager(this.eventManager);
        this.inputController.install();
        for (Enemy e : this.level.getAllEnemies()) {
            this.eventManager.subscribe(EnemyDiedEvent.class, e);
        }
    }

    @Override
    public void update(double deltaTime) {
        this.level.getAllEnemies().forEach(obj -> {
            obj.update(deltaTime);
            if (obj instanceof ShooterEnemyImpl) {
                ((ShooterEnemyImpl) obj).attack().ifPresent(projectile -> {

                    level.addProjectile(projectile);

                    this.eventManager.subscribe(
                            ProjectileSolidEvent.class,
                            projectile);
                });
            }
        });
        this.level.getAllProjectiles().forEach(p -> p.update(deltaTime));
        this.level.getAllPlayers().forEach(p -> p.update(deltaTime, inputController));
        this.level.getInteractiveObstacles().stream()
                .filter(PushBox.class::isInstance)
                .map(PushBox.class::cast)
                .forEach(box -> box.update(deltaTime));

        level.getInteractiveObstacles().stream()
                .filter(PlatformImpl.class::isInstance)
                .map(PlatformImpl.class::cast)
                .forEach(p -> p.update(deltaTime));
        this.collisionManager.manageCollisions(this.level.getAllCollidables());
        this.level.cleanProjectiles();
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