package it.unibo.elementsduo.controller.subcontroller.impl;


import javax.swing.JPanel;
import java.awt.event.ActionListener;

import it.unibo.elementsduo.controller.maincontroller.api.HomeNavigation;
import it.unibo.elementsduo.controller.subcontroller.api.Controller;
import it.unibo.elementsduo.view.MenuPanel;

public class HomeController implements Controller{

    private final MenuPanel view;
    private final HomeNavigation controller;

    private final Runnable startNewGameAction;
    private final Runnable loadGameAction;     

    private ActionListener startListener; 
    private ActionListener loadListener;  
    

    public HomeController(final MenuPanel panel, final HomeNavigation controller, 
                      final Runnable startNewGameAction, final Runnable loadGameAction){
        this.view = panel;
        this.controller = controller;
        this.startNewGameAction = startNewGameAction; 
        this.loadGameAction = loadGameAction;  
    }
    @Override
    public void activate() {

        this.startListener = e -> {
        this.startNewGameAction.run();
        };
        this.view.getStartButton().addActionListener(this.startListener);

        this.loadListener = e -> {
            this.loadGameAction.run();
        };
        this.view.getLoadButton().addActionListener(this.loadListener);
    }

    @Override
    public void deactivate() {
        if (this.startListener != null) {
            this.view.getStartButton().removeActionListener(this.startListener);
        }
        if (this.loadListener != null) {
            this.view.getLoadButton().removeActionListener(this.loadListener);
        }
    }

    @Override
    public JPanel getPanel() {
        return this.view;
    }
    
}

