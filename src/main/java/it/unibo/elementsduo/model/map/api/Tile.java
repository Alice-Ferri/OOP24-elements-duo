package it.unibo.elementsduo.model.map.api;

public interface Tile {

    TileType getType();

    //pavimento sempre, lava da solo fireboy e acqua da watergirl
    boolean isWalkable();

    //carattere usato sul file txt
    char getChar();
}
