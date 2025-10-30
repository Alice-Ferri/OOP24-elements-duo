package it.unibo.elementsduo.controller.gamecontroller.impl;

import javax.swing.JPanel;

import it.unibo.elementsduo.controller.GameLoop;
import it.unibo.elementsduo.controller.enemiesController.api.EnemiesMoveManager;
import it.unibo.elementsduo.controller.enemiesController.impl.EnemiesMoveManagerImpl;
import it.unibo.elementsduo.controller.gameTimer.GameTimer;
import it.unibo.elementsduo.controller.gamecontroller.api.GameController;
import it.unibo.elementsduo.controller.inputController.impl.InputControllerImpl;
import it.unibo.elementsduo.controller.maincontroller.api.GameNavigation;
import it.unibo.elementsduo.controller.progresscontroller.api.ProgressionManager;
import it.unibo.elementsduo.controller.progresscontroller.impl.ProgressionManagerImpl;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionManager;
import it.unibo.elementsduo.model.collisions.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.ProjectileSolidEvent;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.controller.inputController.api.InputController;

import it.unibo.elementsduo.model.collisions.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.ProjectileSolidEvent;
import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.gamestate.impl.GameStateImpl;

import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.view.LevelPanel;

public class GameControllerImpl implements GameController {

    private final Level level;
    private final LevelPanel view;
    private final GameLoop gameLoop;
    private final GameTimer gameTimer;
    private final EnemiesMoveManager moveManager;
    private final GameNavigation controller;
    private final CollisionManager collisionManager;
    private final InputController inputController;
    private final EventManager eventManager = new EventManager();
    private final GameState gameState;
    private ProgressionManagerImpl progressionManager; 

   public GameControllerImpl(final Level level, final GameNavigation controller, final ProgressionManagerImpl progressionManager) { 
        this.level = level;
        this.progressionManager = progressionManager;
        this.view = new LevelPanel(this.level);
        this.gameLoop = new GameLoop(this);
        this.gameTimer = new GameTimer();
        this.moveManager = new EnemiesMoveManagerImpl(level.getAllObstacles());
        this.controller = controller;
        this.collisionManager = new CollisionManager(this.eventManager);
        this.inputController = new InputControllerImpl();
        this.inputController.install();
        for (Enemy e : this.level.getAllEnemies()) {
            this.eventManager.subscribe(EnemyDiedEvent.class, e);
        }
        gameState = new GameStateImpl(eventManager, level);
    }

    @Override
    public void update(double deltaTime) {

        if (gameState.isGameOver()) {
            handleGameOver();
            return;
        }

        this.level.getLivingEnemies().forEach(obj -> {
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
        this.level.getAllInteractiveObstacles().stream()
                .filter(PushBox.class::isInstance)
                .map(PushBox.class::cast)
                .forEach(box -> box.update(deltaTime));

        level.getAllInteractiveObstacles().stream()
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
        this.gameTimer.start();
    }

    @Override
    public void activate() {
        this.start();
        this.view.getHomeButton().addActionListener(e -> {
            controller.goToMenu();
        });
        this.view.getLevelSelectButton().addActionListener(e -> {
            controller.goToLevelSelection();
        });
    }

    @Override
    public void deactivate() {
        this.gameLoop.stop();
        this.gameTimer.stop();
        this.inputController.uninstall();
    }

    @Override
    public JPanel getPanel() {
        return this.view;
    }

    private void handleGameOver() {
        if (gameState.didWin()) {
            System.out.println("Gioco Terminato");
            this.progressionManager.levelCompleted(this.progressionManager.getCurrentState().getCurrentLevel(),50,500);
            this.controller.goToLevelSelection();    
        } else {
            this.controller.restartCurrentLevel();
        }
    }

}
