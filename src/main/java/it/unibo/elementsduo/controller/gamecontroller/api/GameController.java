package it.unibo.elementsduo.controller.api;

import it.unibo.elementsduo.controller.subcontroller.api.Controller;

public interface GameController extends Controller{
    
    public void update(double deltaTime);

    public void render();

}
