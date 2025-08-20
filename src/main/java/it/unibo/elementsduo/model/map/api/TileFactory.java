package it.unibo.elementsduo.model.map.api;

import it.unibo.elementsduo.resources.Position;

public interface TileFactory {
    
    Tile createTile(char symbol, Position pos);
}
