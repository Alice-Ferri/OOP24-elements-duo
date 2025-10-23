package it.unibo.elementsduo.controller.impl;

import it.unibo.elementsduo.view.MainMenuPanel;

public class MenuController {

    public MenuController(final MainMenuPanel view, final Runnable onStart) {
        
        view.getStartButton().addActionListener(e -> onStart.run());
       
    }
}
