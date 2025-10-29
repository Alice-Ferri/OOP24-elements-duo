package it.unibo.elementsduo.model.gamestate.impl;

import java.util.Objects;

import it.unibo.elementsduo.model.events.api.Event;
import it.unibo.elementsduo.model.events.api.EventListener;
import it.unibo.elementsduo.model.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.events.impl.EventManager;
import it.unibo.elementsduo.model.events.impl.FireExitEvent;
import it.unibo.elementsduo.model.events.impl.GemCollectedEvent;
import it.unibo.elementsduo.model.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.events.impl.WaterExitEvent; 
import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.map.level.api.Level;

public class GameStateImpl implements EventListener, GameState {

    private final Level level;
    private boolean gameOver = false;
    private boolean won = false;
    private int gemsCollected = 0;
    private int deadEnemies = 0;
    private boolean fireboyReachedExit = false; 
    private boolean watergirlReachedExit = false; 

    public GameStateImpl(final EventManager eventManager, final Level level) {
        Objects.requireNonNull(eventManager);
        Objects.requireNonNull(level);
        this.level = level;

        eventManager.subscribe(PlayerDiedEvent.class, this);
        eventManager.subscribe(GemCollectedEvent.class, this);
        eventManager.subscribe(EnemyDiedEvent.class, this);
        eventManager.subscribe(FireExitEvent.class, this);
        eventManager.subscribe(WaterExitEvent.class, this);
    }

    @Override
    public void onEvent(Event event) {
        if (gameOver) {
            return;
        }

        boolean checkWin = false;

        if (event instanceof PlayerDiedEvent e) {
            handlePlayerDied(e);
        } else if (event instanceof GemCollectedEvent e) {
            handleGemCollected(e);
        } else if (event instanceof EnemyDiedEvent e) {
            handleEnemyDied(e);
        } else if (event instanceof FireExitEvent e) { 
            handleFireReachedExit(e);
            checkWin = true;
        } else if (event instanceof WaterExitEvent e) {
            handleWaterReachedExit(e);
            checkWin = true;
        }

        if (checkWin) {
            checkGameWinCondition();
        }
    }

    private void handleEnemyDied(EnemyDiedEvent e) {
        e.getEnemy().die();
        this.deadEnemies++;
    }

    private void handleGemCollected(GemCollectedEvent e) {
        this.gemsCollected++;
    }

    private void handlePlayerDied(PlayerDiedEvent e) {
        endGame(false);
    }

    private void handleFireReachedExit(FireExitEvent e) {
        System.out.println("fire uscito");
        this.fireboyReachedExit = true;
    }

    private void handleWaterReachedExit(WaterExitEvent e) {
        System.out.println("acua uscito");
        this.watergirlReachedExit = true;
    }

    private void checkGameWinCondition() {
        if (!gameOver && this.fireboyReachedExit && this.watergirlReachedExit) {
            System.out.println("Vinto");
            endGame(true);
        }
    }

    private void endGame(boolean won) {
        if (!gameOver) {
            this.gameOver = true;
            this.won = won;
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

    public int getEnemiesDefeated() {
        return this.deadEnemies;
    }

    public boolean hasFireboyReachedExit() {
        return this.fireboyReachedExit;
    }

    public boolean hasWatergirlReachedExit() {
        return this.watergirlReachedExit;
    }
}