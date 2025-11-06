package it.unibo.elementsduo.model.gamestate.impl;

import java.util.Objects;

import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.interactions.events.api.Event;
import it.unibo.elementsduo.model.interactions.events.api.EventListener;
import it.unibo.elementsduo.model.interactions.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.interactions.events.impl.EventManager;
import it.unibo.elementsduo.model.interactions.events.impl.FireExitEvent;
import it.unibo.elementsduo.model.interactions.events.impl.GemCollectedEvent;
import it.unibo.elementsduo.model.interactions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.interactions.events.impl.WaterExitEvent;

/**
 * Implementation of {@link GameState}.
 * Listens to game events to track the game's progress,
 * such as winning, losing, and collecting items.
 */
public final class GameStateImpl implements EventListener, GameState {

    private boolean gameOver;
    private boolean won;
    private int gemsCollected;
    private int deadEnemies;
    private boolean fireboyReachedExit;
    private boolean watergirlReachedExit;

    /**
     * Constructs a new GameState and subscribes it to relevant game events.
     *
     * @param eventManager The main {@link EventManager} to subscribe to.
     */
    public GameStateImpl(final EventManager eventManager) {
        Objects.requireNonNull(eventManager);

        eventManager.subscribe(PlayerDiedEvent.class, this);
        eventManager.subscribe(GemCollectedEvent.class, this);
        eventManager.subscribe(EnemyDiedEvent.class, this);
        eventManager.subscribe(FireExitEvent.class, this);
        eventManager.subscribe(WaterExitEvent.class, this);
    }

    @Override
    public void onEvent(final Event event) {
        if (gameOver) {
            return;
        }

        if (event instanceof PlayerDiedEvent) {
            handlePlayerDied();
        } else if (event instanceof GemCollectedEvent) {
            handleGemCollected();
        } else if (event instanceof EnemyDiedEvent) {
            handleEnemyDied();
        } else if (event instanceof FireExitEvent) {
            handleFireReachedExit();
            checkGameWinCondition();
        } else if (event instanceof WaterExitEvent) {
            handleWaterReachedExit();
            checkGameWinCondition();
        }
    }

    private void handleEnemyDied() {
        this.deadEnemies++;
    }

    private void handleGemCollected() {
        this.gemsCollected++;
    }

    private void handlePlayerDied() {
        endGame(false);
    }

    private void handleFireReachedExit() {
        this.fireboyReachedExit = true;
    }

    private void handleWaterReachedExit() {
        this.watergirlReachedExit = true;
    }

    private void checkGameWinCondition() {
        if (!gameOver && this.fireboyReachedExit && this.watergirlReachedExit) {
            endGame(true);
        }
    }

    private void endGame(final boolean gameWon) {
        if (!gameOver) {
            this.gameOver = true;
            this.won = gameWon;
        }
    }

    @Override
    public boolean isGameOver() {
        return this.gameOver;
    }

    @Override
    public boolean didWin() {
        return this.won;
    }

    @Override
    public int getGemsCollected() {
        return this.gemsCollected;
    }

    @Override
    public int getEnemiesDefeated() {
        return this.deadEnemies;
    }

    /**
     * Checks if Fireboy has reached the exit door.
     *
     * @return true if Fireboy has reached the exit, false otherwise.
     */
    public boolean hasFireboyReachedExit() {
        return this.fireboyReachedExit;
    }

    /**
     * Checks if Watergirl has reached the exit door.
     *
     * @return true if Watergirl has reached the exit, false otherwise.
     */
    public boolean hasWatergirlReachedExit() {
        return this.watergirlReachedExit;
    }
}
