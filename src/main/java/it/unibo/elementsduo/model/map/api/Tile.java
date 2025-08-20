package it.unibo.elementsduo.model.map.api;

import it.unibo.elementsduo.resources.Position;

public interface Tile {

    TileType getType();

    //pavimento sempre, lava da solo fireboy e acqua da watergirl
    boolean isWalkable();

    //carattere usato sul file txt
    char getChar();

    Position getPosition();

}
