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
import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.collisions.events.api.EventListener;
import it.unibo.elementsduo.model.collisions.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.ProjectileSolidEvent;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.ManagerInjectable;
import it.unibo.elementsduo.controller.inputController.api.InputController;
import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.gamestate.impl.GameStateImpl;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.mission.impl.MissionManager;
import it.unibo.elementsduo.model.powerups.impl.PowerUpManager;
import it.unibo.elementsduo.view.LevelPanel;

/**
 * Manages the logic for a single game level.
 * This class initializes all game systems (e.g, input, collisions, events...)
 * and runs the main game loop, updating and rendering the level.
 */
public final class GameControllerImpl implements EventListener, GameController {

    private final Level level;
    private final LevelPanel view;
    private final GameNavigation controller;
    private final GameLoop gameLoop;
    private final EventManager eventManager;
    private final GameState gameState;
    private final MissionManager scoreManager;
    private final InputController inputController;
    private final PowerUpManager powerUpManager;
    private final CollisionManager collisionManager;
    private final EnemiesMoveManager moveManager;
    private final GameTimer gameTimer;
    private final ProgressionManagerImpl progressionManager;

    private boolean entitiesNeedCleaning;

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
        this.powerUpManager = new PowerUpManager(this.eventManager);
        this.collisionManager = new CollisionManager(this.eventManager);
        this.moveManager = new EnemiesMoveManagerImpl(level.getAllObstacles());
        this.gameTimer = new GameTimer();
        this.scoreManager = new MissionManager(level);
        this.progressionManager = progressionManager;

        eventManager.subscribe(ProjectileSolidEvent.class, this);
        eventManager.subscribe(EnemyDiedEvent.class, this);
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

        updatePlayers(deltaTime);
        checkEnemiesAttack();
        this.level.getAllUpdatables().forEach(e -> e.update(deltaTime));
        this.collisionManager.manageCollisions(this.level.getAllCollidables());

        if (entitiesNeedCleaning) {
            this.level.cleanInactiveEntities();
            entitiesNeedCleaning = false;
        }
    }

    @Override
    public void render() {
        this.view.repaint();
    }

    @Override
    public void onEvent(final Event event) {
        if (event instanceof EnemyDiedEvent || event instanceof ProjectileSolidEvent) {
            this.entitiesNeedCleaning = true;
        }
    }

    private void checkEnemiesAttack() {
        this.level.getLivingEnemies().forEach(e -> e.attack().ifPresent(projectile -> level.addProjectile(projectile)));
    }

    private void updatePlayers(double dt) {
        this.level.getAllPlayers().forEach(e -> e.update(dt, inputController));
    }

    private void handleGameOver() {
        this.gameTimer.stop();
        this.gameLoop.stop();
        this.scoreManager.calculateFinalScore(gameState, gameTimer.getElapsedSeconds());

        SwingUtilities.invokeLater(() -> {
            if (gameState.didWin()) {
                JOptionPane.showMessageDialog(
                        this.view,
                        "Level Completed!",
                        "WIN!",
                        JOptionPane.INFORMATION_MESSAGE);
                this.progressionManager.levelCompleted(
                        this.progressionManager.getCurrentState().getCurrentLevel(),
                        this.gameTimer.getElapsedSeconds(),
                        this.scoreManager.areAllObjectivesComplete());
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
