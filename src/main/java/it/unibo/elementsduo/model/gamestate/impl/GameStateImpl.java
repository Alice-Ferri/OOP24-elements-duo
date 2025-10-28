package it.unibo.elementsduo.model.gamestate.impl;

import java.util.Objects;

import it.unibo.elementsduo.model.events.api.Event;
import it.unibo.elementsduo.model.events.api.EventListener;
import it.unibo.elementsduo.model.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.events.impl.EventManager;
import it.unibo.elementsduo.model.events.impl.GemCollectedEvent;
import it.unibo.elementsduo.model.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.gamestate.api.GameState;

public class GameStateImpl implements EventListener, GameState{

    private boolean gameOver = false;
    private boolean won = false;
    private int gemsCollected = 0;

    public GameStateImpl(final EventManager eventManager) {
        Objects.requireNonNull(eventManager);
    }

    @Override
    public void onEvent(Event event) {
        if (gameOver) return;

        if (event instanceof PlayerDiedEvent e) {
            handlePlayerDied(e); 
        } else if (event instanceof GemCollectedEvent e) {
            handleGemCollected(e);
        } else if (event instanceof EnemyDiedEvent e) {
            handleEnemyDied(e);
        }

    }

    private void handleEnemyDied(EnemyDiedEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleEnemyDied'");
    }

    private void handleGemCollected(GemCollectedEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleGemCollected'");
    }

    private void handlePlayerDied(PlayerDiedEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePlayerDied'");
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
    
}
