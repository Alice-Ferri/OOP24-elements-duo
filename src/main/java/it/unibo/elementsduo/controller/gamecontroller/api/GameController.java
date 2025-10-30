package it.unibo.elementsduo.controller.gamecontroller.api;

import it.unibo.elementsduo.controller.subcontroller.api.Controller;

public interface GameController extends Controller{
    
    void update(double deltaTime);

    void render();

}
