package it.unibo.elementsduo.model.map.api;

import java.util.Optional;
import java.util.Set;

import it.unibo.elementsduo.resources.Position;

public interface Level {
    
    boolean addTile(Position pos,Tile t);
    boolean removeTile(Position pos);
    boolean removeTile(Position pos,Tile t);

    Optional<Tile> getTileAt(Position pos);
    
    Set<Tile> getAllTiles();

    Set<Tile> getTileOfType(TileType type);
    Set<Tile> getWalls();
    Set<Tile> getFloors();
    Set<Tile> getFireSpawn();
    Set<Tile> getWaterSpawn();
    Set<Tile> getFireExit();
    Set<Tile> getWaterExit();

    Set<Position> getPositionsOfType(TileType type);
    Set<Position> getFireSpawnPositions();
    Set<Position> getWaterSpawnPositions();
    Set<Position> getFireExitPositions();
    Set<Position> getWaterExitPositions();

    void setLevelWon();
    void setLevelLost();

    boolean isLevelWon();
    boolean isLevelLost();

}
