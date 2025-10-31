package it.unibo.elementsduo.controller.subcontroller.impl;


import javax.swing.JPanel;
import it.unibo.elementsduo.controller.maincontroller.api.HomeNavigation;
import it.unibo.elementsduo.controller.subcontroller.api.Controller;
import it.unibo.elementsduo.view.MenuPanel;

public class HomeController implements Controller {

    private final MenuPanel view;
    private final HomeNavigation controller;

    public HomeController(final MenuPanel panel, final HomeNavigation controller){
        this.view = panel;
        this.controller = controller;
    }
    @Override
    public void activate() {

        this.view.getStartButton().addActionListener(e -> controller.startNewGame());

        this.view.getLoadButton().addActionListener(e -> controller.loadSavedGame());

        this.view.getGuideButton().addActionListener(e -> controller.gameGuide());
    }

    @Override
    public void deactivate() {
        
        this.view.getStartButton().removeActionListener(null);
        
        this.view.getLoadButton().removeActionListener(null);

        this.view.getGuideButton().removeActionListener(null);
        
    }

    @Override
    public JPanel getPanel() {
        return this.view;
    }
    
}

