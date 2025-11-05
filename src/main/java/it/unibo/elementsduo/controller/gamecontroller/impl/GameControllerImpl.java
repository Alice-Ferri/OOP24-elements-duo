package it.unibo.elementsduo.controller.gamecontroller.impl;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.awt.event.ActionListener;
import java.util.Objects;
import it.unibo.elementsduo.controller.GameLoop;
import it.unibo.elementsduo.controller.enemiescontroller.api.EnemiesMoveManager;
import it.unibo.elementsduo.controller.enemiescontroller.impl.EnemiesMoveManagerImpl;
import it.unibo.elementsduo.controller.gamecontroller.api.GameController;
import it.unibo.elementsduo.controller.inputcontroller.api.InputController;
import it.unibo.elementsduo.controller.inputcontroller.impl.InputControllerImpl;
import it.unibo.elementsduo.controller.maincontroller.api.GameNavigation;
import it.unibo.elementsduo.controller.progresscontroller.impl.ProgressionManagerImpl;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionManager;
import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.collisions.events.api.EventListener;
import it.unibo.elementsduo.model.collisions.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PowerUpCollectedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.ProjectileSolidEvent;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.ManagerInjectable;
import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.gamestate.impl.GameStateImpl;
import it.unibo.elementsduo.model.map.level.MapLoader;
import it.unibo.elementsduo.model.map.level.impl.Level;
import it.unibo.elementsduo.model.map.mapvalidator.api.InvalidMapException;
import it.unibo.elementsduo.model.map.mapvalidator.impl.MapValidatorImpl;
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
    private final ActionListener onMenuListener;
    private final ActionListener onLevelListener;

    private boolean entitiesNeedCleaning;

    /**
     * Constructs a new GameController for a specific level.
     *
     * @param currentLevel       The current level
     * @param controller         The main navigation controller.
     * @param progressionManager The manager for saving game progress.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Intentional Dependency Injection: ProgressionManager is a shared service.")

    public GameControllerImpl(final int currentLevel, final GameNavigation controller,
            final ProgressionManagerImpl progressionManager)
            throws InvalidMapException {
        this.controller = Objects.requireNonNull(controller);
        this.gameLoop = new GameLoop(this);
        this.eventManager = new EventManager();
        this.inputController = new InputControllerImpl();
        this.gameState = new GameStateImpl(eventManager);
        this.powerUpManager = new PowerUpManager(this.eventManager);
        this.collisionManager = new CollisionManager(this.eventManager);
        this.gameTimer = new GameTimer();
        this.progressionManager = progressionManager;
        this.onLevelListener = e -> controller.goToLevelSelection();
        this.onMenuListener = e -> controller.goToMenu();
        final MapValidatorImpl mapValidator = new MapValidatorImpl();
        MapLoader mapLoader = new MapLoader();
        level = new Level(mapLoader.loadLevel(currentLevel));
        mapValidator.validate(level);
        this.view = new LevelPanel(level);
        this.moveManager = new EnemiesMoveManagerImpl(level.getAllObstacles());
        this.scoreManager = new MissionManager(level);
        this.level.getAllPlayers().forEach(this.powerUpManager::registerPlayer);

        eventManager.subscribe(ProjectileSolidEvent.class, this);
        eventManager.subscribe(EnemyDiedEvent.class, this);
    }

    @Override
    public void activate() {
        this.inputController.install();

        this.view.addButtonsListeners(onMenuListener, onLevelListener);

        this.setEnemiesMoveManager(moveManager);
        this.gameLoop.start();
        this.gameTimer.start();
    }

    @Override
    public void deactivate() {
        this.gameLoop.stop();
        this.gameTimer.stop();
        this.inputController.uninstall();

        view.removeButtonsListeners(onMenuListener, onLevelListener);
    }

    @Override
    @SuppressFBWarnings(value = "EI", justification = "Required to MainController to add it to the JFrame's card layout")
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
        this.powerUpManager.update(deltaTime);

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
        if (event instanceof EnemyDiedEvent || event instanceof ProjectileSolidEvent
                || event instanceof PowerUpCollectedEvent) {
            this.entitiesNeedCleaning = true;
        }
    }

    private void checkEnemiesAttack() {
        this.level.getLivingEnemies().forEach(e -> e.attack().ifPresent(level::addProjectile));
    }

    private void updatePlayers(final double dt) {
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
