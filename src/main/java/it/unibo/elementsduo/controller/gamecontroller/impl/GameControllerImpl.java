package it.unibo.elementsduo.controller.gamecontroller.impl;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.Objects;
import it.unibo.elementsduo.controller.GameLoop;
import it.unibo.elementsduo.controller.enemiescontroller.api.EnemiesMoveManager;
import it.unibo.elementsduo.controller.enemiescontroller.impl.EnemiesMoveManagerImpl;
import it.unibo.elementsduo.controller.gamecontroller.api.GameController;
import it.unibo.elementsduo.controller.inputController.impl.InputControllerImpl;
import it.unibo.elementsduo.controller.maincontroller.api.GameNavigation;
import it.unibo.elementsduo.controller.progresscontroller.impl.ProgressionManagerImpl;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionManager;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.ManagerInjectable;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.controller.inputController.api.InputController;
import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.gamestate.impl.GameStateImpl;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.view.LevelPanel;

/**
 * Manages the logic for a single game level.
 * This class initializes all game systems (e.g, input, collisions, events...)
 * and runs the main game loop, updating and rendering the level.
 */
public final class GameControllerImpl implements GameController {
    private static final int GEMS_COLLECTED_PLACEHOLDER = 500;

    private final Level level;
    private final LevelPanel view;
    private final GameNavigation controller;
    private final GameLoop gameLoop;
    private final EventManager eventManager;
    private final GameState gameState;
    private final InputController inputController;
    private final CollisionManager collisionManager;
    private final EnemiesMoveManager moveManager;
    private final GameTimer gameTimer;
    private final ProgressionManagerImpl progressionManager;

    /**
     * Constructs a new GameController for a specific level.
     *
     * @param level              The game level model.
     * @param controller         The main navigation controller.
     * @param view               The level's view panel.
     * @param progressionManager The manager for saving game progress.
     */
    public GameControllerImpl(final Level level, final GameNavigation controller,
                              final LevelPanel view, final ProgressionManagerImpl progressionManager) {
        this.level = Objects.requireNonNull(level);
        this.controller = Objects.requireNonNull(controller);
        this.view = Objects.requireNonNull(view);
        this.gameLoop = new GameLoop(this);
        this.eventManager = new EventManager();
        this.inputController = new InputControllerImpl();
        this.gameState = new GameStateImpl(eventManager);
        this.collisionManager = new CollisionManager(this.eventManager);
        this.moveManager = new EnemiesMoveManagerImpl(level.getAllObstacles());
        this.gameTimer = new GameTimer();
        this.progressionManager = progressionManager;
    }

    @Override
    public void activate() {
        this.inputController.install();

        this.view.getHomeButton().addActionListener(e -> controller.goToMenu());
        this.view.getLevelSelectButton().addActionListener(e -> controller.goToLevelSelection());

        this.setEnemiesMoveManager(moveManager);
        this.gameLoop.start();
        this.gameTimer.start();
    }

    @Override
    public void deactivate() {
        this.gameLoop.stop();
        this.inputController.uninstall();

        for (final var listener : this.view.getHomeButton().getActionListeners()) {
            this.view.getHomeButton().removeActionListener(listener);
        }
        for (final var listener : this.view.getLevelSelectButton().getActionListeners()) {
            this.view.getLevelSelectButton().removeActionListener(listener);
        }
    }

    @Override
    public JPanel getPanel() {
        return this.view;
    }

    @Override
    public void update(final double deltaTime) {
        if (gameState.isGameOver()) {
            handleGameOver();
            return;
        }

        updateEnemies(deltaTime);
        updateProjectiles(deltaTime);
        updatePlayers(deltaTime);
        updateInteractiveObstacles(deltaTime);

        this.collisionManager.manageCollisions(this.level.getAllCollidables());

        this.level.cleanInactiveEntities();
        this.level.cleanProjectiles();

    }

    @Override
    public void render() {
        this.view.repaint();
    }

    private void updateEnemies(final double deltaTime) {
        this.level.getLivingEnemies().forEach(enemy -> {
            enemy.update(deltaTime);
            if (enemy instanceof ShooterEnemyImpl shooter) {
                shooter.attack().ifPresent(projectile -> {
                    level.addProjectile(projectile);
                });
            }
        });
    }

    private void updateProjectiles(final double deltaTime) {
        this.level.getAllProjectiles().forEach(p -> p.update(deltaTime));
    }

    private void updatePlayers(final double deltaTime) {
        this.level.getAllPlayers().forEach(p -> p.update(deltaTime, inputController));
    }

    private void updateInteractiveObstacles(final double deltaTime) {
        this.level.getAllInteractiveObstacles().stream()
                .filter(PushBox.class::isInstance)
                .map(PushBox.class::cast)
                .forEach(box -> box.update(deltaTime));
        this.level.getAllInteractiveObstacles().stream()
                .filter(PlatformImpl.class::isInstance)
                .map(PlatformImpl.class::cast)
                .forEach(p -> p.update(deltaTime));
    }

    private void handleGameOver() {
        this.gameTimer.stop();
        this.gameLoop.stop();

        SwingUtilities.invokeLater(() -> {
            if (gameState.didWin()) {
                JOptionPane.showMessageDialog(
                        this.view,
                        "Level Completed!",
                        "WIN!",
                        JOptionPane.INFORMATION_MESSAGE
                );
                this.progressionManager.levelCompleted(
                        this.progressionManager.getCurrentState().getCurrentLevel(),
                        this.gameTimer.getElapsedSeconds(),
                        GEMS_COLLECTED_PLACEHOLDER 
                );
                this.controller.goToLevelSelection();
            } else {
                this.controller.restartCurrentLevel();
            }
        });
    }

    private void setEnemiesMoveManager(final EnemiesMoveManager manager) {
        this.level.getEntitiesByClass(Enemy.class).stream()
                .filter(ManagerInjectable.class::isInstance)
                .map(ManagerInjectable.class::cast)
                .forEach(injectableEnemy -> injectableEnemy.setMoveManager(manager));
    }
}
