package it.unibo.elementsduo.model.map.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.map.api.Tile;
import it.unibo.elementsduo.model.map.api.TileType;
import it.unibo.elementsduo.resources.Position;

public class LevelImpl implements Level{

    private final Map<Position,Tile> mapTiles;
    private boolean win;
    private boolean lost;

    public LevelImpl(){
        mapTiles = new HashMap<>();
    }

    public LevelImpl(final Map<Position,Tile> map){
        mapTiles = new HashMap<>(Objects.requireNonNull(map));
    }
    

    @Override
    public boolean addTile(Position pos,Tile t) {
        Objects.requireNonNull(pos);
        Objects.requireNonNull(t);
        return this.mapTiles.putIfAbsent(pos, t) == null;
    }

    @Override
    public boolean removeTile(final Position pos) {
        Objects.requireNonNull(pos);
        return this.mapTiles.remove(pos) != null;
    }

    @Override
    public boolean removeTile(Position pos,Tile t) {
        Objects.requireNonNull(pos);
        Objects.requireNonNull(t);
        return this.mapTiles.remove(pos, t);
    }

    @Override
    public Optional<Tile> getTileAt(Position pos) {
        return pos==null ? Optional.empty() : Optional.of(mapTiles.get(pos));
    }

    @Override
    public Set<Tile> getAllTiles() {
        return Collections.unmodifiableSet(new HashSet<>(mapTiles.values()));
    }

    @Override
    public Set<Tile> getTileOfType(TileType type) {
        Objects.requireNonNull(type);
        return mapTiles.values().stream().filter( t -> t.getType().equals(type))
        .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<Tile> getWalls() {
        return getTileOfType(TileType.WALL);
    }

    @Override
    public Set<Tile> getFloors() {
        return getTileOfType(TileType.FLOOR);
    }

    @Override
    public Set<Tile> getFireSpawn() {
        return getTileOfType(TileType.FIRESPAWN);
    }

    @Override
    public Set<Tile> getWaterSpawn() {
        return getTileOfType(TileType.WATERSPAWN);
    }

    @Override
    public Set<Tile> getFireExit() {
        return getTileOfType(TileType.FIREEXIT);
    }

    @Override
    public Set<Tile> getWaterExit() {
        return getTileOfType(TileType.WATEREXIT);
    }

    @Override
    public Set<Position> getPositionsOfType(final TileType type) {
        Objects.requireNonNull(type);
        return mapTiles.entrySet().stream()
                .filter(e -> e.getValue().getType() == type)
                .map(Map.Entry::getKey)
                .collect(Collectors.toUnmodifiableSet());
    }

    public Set<Position> getFireSpawnPositions() {
        return getPositionsOfType(TileType.FIRESPAWN);
    }

    public Set<Position> getWaterSpawnPositions() {
        return getPositionsOfType(TileType.WATERSPAWN);
    }

    public Set<Position> getFireExitPositions() {
        return getPositionsOfType(TileType.FIREEXIT);
    }

    public Set<Position> getWaterExitPositions() {
        return getPositionsOfType(TileType.WATEREXIT);
    }


    @Override
    public void setLevelWon() {
        this.win=true;
    }

    @Override
    public void setLevelLost() {
        this.lost=true;
    }

    @Override
    public boolean isLevelWon() {
        return this.win;
    }

    @Override
    public boolean isLevelLost() {
        return this.lost;
    }

    
    
}
