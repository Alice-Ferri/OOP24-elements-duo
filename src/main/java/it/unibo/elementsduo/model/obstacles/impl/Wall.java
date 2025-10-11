package it.unibo.elementsduo.model.obstacles.impl;

import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.utils.Position;

public class Wall implements obstacle {

    private final Position pos;

    public Wall(Position pos) {
        this.pos=pos;
    }

    @Override
    public Position getPos() {
        return this.pos;
    }

    

}
